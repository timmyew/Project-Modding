package de.projectmodding.core.service;

import de.projectmodding.core.component.event.Event;
import de.projectmodding.core.component.event.Listener;
import de.projectmodding.core.component.factory.file.*;
import de.projectmodding.core.component.factory.file.scriptblock.ScriptBlockFactory;
import de.projectmodding.core.enums.ModDataEnum;
import de.projectmodding.core.enums.ModDataKey;
import de.projectmodding.core.exception.ModGenerationServiceException;
import de.projectmodding.core.model.definition.DefinitionVersionMap;
import de.projectmodding.core.model.definition.FileDefinitionModel;
import de.projectmodding.core.model.definition.ModDefinitionModel;
import de.projectmodding.core.model.event.ReloadEvent;
import de.projectmodding.core.model.intern.Pair;
import de.projectmodding.core.model.mod.ModData;
import de.projectmodding.core.model.mod.ModPackageModel;
import de.projectmodding.core.model.mod.files.*;
import de.projectmodding.core.model.mod.files.data.ScriptBlock;
import de.projectmodding.core.util.MathUtils;
import de.projectmodding.core.util.VersionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class ModDataGenerationService implements Listener {
    private final HashMap<ModDataKey, IFactory<? extends BaseFile>> fileFactoryMap = new HashMap<>();
    private HashMap<String, HashMap<String, HashMap<String, List<String>>>> cachedScriptBlockTypes;
    private final RuntimeDataService runtimeDataService;

    public ModDataGenerationService(RuntimeDataService runtimeDataService) {
        this.runtimeDataService = runtimeDataService;
        init();
    }

    public BaseFile createFile(ModPackageModel modPackageModel, String modName, ModDataKey key, String version) {
        ModDefinitionModel definition = runtimeDataService.getByType(DefinitionVersionMap.class).getMap().get(version);

        if (fileFactoryMap.containsKey(key)) {
            final BaseFile baseFile = fileFactoryMap.get(key).create(definition, modPackageModel.getRootPath());

            modPackageModel.getModList().forEach(mod -> {
                if (mod.getModName().equals(modName)) {
                    ModData modData = version.equals(ModDataEnum.COMMON.getValue()) ? mod.getCommon() : mod.getModVersionMap().get(version);
                    String newFileName = "";

                    switch (key) {
                        case Animation:
                            newFileName = baseFile.getFileName().concat(String.valueOf(getUniqueNumber(modData.getAnimationFiles())));
                            modData.getAnimationFiles().add((AnimationModel) baseFile);
                            break;
                        case Model_X:
                            newFileName = baseFile.getFileName().concat(String.valueOf(getUniqueNumber(modData.getModel_X_files())));
                            modData.getModel_X_files().add((Model_X_Model) baseFile);
                            break;
                        case Sandbox:
                            newFileName = baseFile.getFileName().concat(String.valueOf(getUniqueNumber(modData.getSandboxFiles())));
                            modData.getSandboxFiles().add((SandboxModel) baseFile);
                            break;
                        case Script:
                            newFileName = baseFile.getFileName().concat(String.valueOf(getUniqueNumber(modData.getScriptFiles())));
                            modData.getScriptFiles().add((ScriptModel) baseFile);
                            break;
                        case Lua:
                            newFileName = baseFile.getFileName().concat(String.valueOf(getUniqueNumber(modData.getLuaFiles())));
                            modData.getLuaFiles().add((LuaModel) baseFile);
                            break;
                        case Sound:
                            newFileName = baseFile.getFileName().concat(String.valueOf(getUniqueNumber(modData.getSoundFiles())));
                            modData.getSoundFiles().add((SoundModel) baseFile);
                            break;
                        case Texture:
                            newFileName = baseFile.getFileName().concat(String.valueOf(getUniqueNumber(modData.getTextureFiles())));
                            modData.getTextureFiles().add((TextureModel) baseFile);
                            break;
                        case Translation:
                            newFileName = baseFile.getFileName().concat(String.valueOf(getUniqueNumber(modData.getTranslationFiles())));
                            modData.getTranslationFiles().add((TranslationModel) baseFile);
                            break;
                    }

                    baseFile.setFileName(newFileName);
                }
            });
            return baseFile;
        } else {
            throw new ModGenerationServiceException(String.format("Failed to generate mod data for key '%s'", key.toString()));
        }
    }

    private <T extends BaseFile> int getUniqueNumber(List<T> baseFiles) {
        int maxNumber = 0;

        for (int i = 0; i < baseFiles.size(); i++) {
            String data = baseFiles.get(i).getFileName();
            int lastDelimiter = data.lastIndexOf('_');
            if (lastDelimiter > -1) {
                String stringNumber = data.substring(lastDelimiter + 1);
                int number = MathUtils.tryParseInt(stringNumber, 0);

                if (number >= maxNumber) {
                    maxNumber = number + 1;
                }
            }
        }

        return maxNumber;
    }

    public List<ScriptBlock> searchAndGetScriptBlocks(String modName, String modVersion, String fileName) {
        AtomicReference<List<ScriptBlock>> scriptBlocks = new AtomicReference<>();
        ModPackageModel modPackageModel = runtimeDataService.getByType(ModPackageModel.class);

        modPackageModel.getModList().forEach(mod -> {
            if (mod.getModName().equals(modName)) {
                List<ScriptModel> scriptModels = null;
                if (modVersion.equals(ModDataEnum.COMMON.getValue())) {
                    scriptModels = mod.getCommon().getScriptFiles();
                } else {
                    scriptModels = mod.getModVersionMap().get(modVersion).getScriptFiles();
                }

                scriptModels.forEach(baseFile -> {
                    if (baseFile.getFileName().equals(fileName))
                        scriptBlocks.set(baseFile.getItems());
                });
            }
        });
        return scriptBlocks.get();
    }

    public ScriptBlock createScriptBlock(String modName, String version, String fileName, String name) {
        ScriptBlock scriptBlock = ScriptBlockFactory.createScriptBlock(name);
        iterateScriptBlock(modName, version, scriptModel -> _createScriptBlock(fileName, scriptModel, scriptBlock));
        return scriptBlock;
    }

    //HashMap<parentType, HashMap<parentSubType, List<childData>>>
    public HashMap<String, HashMap<String, List<String>>> loadScriptBlockTypes(String version) {
        if (cachedScriptBlockTypes != null && !cachedScriptBlockTypes.isEmpty() && cachedScriptBlockTypes.containsKey(version)) {
            return cachedScriptBlockTypes.get(version);
        }
        else{
            cachedScriptBlockTypes = new HashMap<>();
            HashMap<String, HashMap<String, List<String>>> versionNode = new HashMap<>();
            ModDefinitionModel modDefinition = getDefinitionModelByVersion(version);

            cachedScriptBlockTypes.put(version, versionNode);


            modDefinition.getScript().getCustomTypeMappings().forEach(mapper -> {
                Pair<String, List<String>> parentDataPair = getResolvedSegmentData(mapper.getParent(), modDefinition.getScript());
                Pair<String, List<String>> childDataPair = getResolvedSegmentData(mapper.getChild(), modDefinition.getScript());


                parentDataPair.getValue().forEach(parentElement -> {
                    HashMap<String,List<String>> typeMapping;

                    if (cachedScriptBlockTypes.containsKey(parentDataPair.getKey())) {
                        typeMapping = versionNode.get(parentDataPair.getKey());

                        if (typeMapping.containsKey(parentElement)) {
                            List<String> elements = typeMapping.get(parentElement);
                            addNotExistingElements(childDataPair.getValue(), elements);
                        }
                        else {
                            typeMapping.put(parentElement, childDataPair.getValue().stream().map(String::new).collect(Collectors.toCollection(ArrayList::new)));
                        }
                    }
                    else{
                        typeMapping = new HashMap<>();
                        typeMapping.put(parentElement, childDataPair.getValue().stream().map(String::new).collect(Collectors.toCollection(ArrayList::new)));
                        versionNode.put(parentDataPair.getKey(), typeMapping);
                    }
                });

            });
        }
        return cachedScriptBlockTypes.get(version);
    }

    private void addNotExistingElements(List<String> srcList, List<String> destList){
        srcList.forEach(src -> {
            int result = destList.indexOf(src);

            if (result == -1) {
                destList.add(src);
            }
        });
    }

    //Pair<First Segment, List<Second Segments>>
    private Pair<String, List<String>> getResolvedSegmentData(String rawSegmentData, FileDefinitionModel definitionModel){
        final int SEGMENT_TYPE_INDEX = 0;
        final int SEGMENT_ELEMENT_INDEX = 1;

        String[] segmentData = rawSegmentData.split("\\.");
        Pair<String, List<String>> resultPair = new  Pair<>();
        List<String> resolvedSegmentData;

        if (segmentData.length == 1) {
            resolvedSegmentData = getCustomDataType(segmentData[SEGMENT_TYPE_INDEX], definitionModel);
        } else if (segmentData.length == 2) {
            resolvedSegmentData = new ArrayList<>();
            resolvedSegmentData.add(segmentData[SEGMENT_ELEMENT_INDEX]);
        } else
            throw new ModGenerationServiceException(String.format(
                    "Invalid custom type mapping: parameter '%s' contains too many segments. "
                            .concat("Expected at most 2 segments (e.g. 'type' or 'type.element'), but got %d."),
                    rawSegmentData, segmentData.length
            ));

        resultPair.setKey(segmentData[SEGMENT_TYPE_INDEX]);
        resultPair.setValue(resolvedSegmentData);
        return resultPair;
    }

    //List<Data>
    private List<String> getCustomDataType(String typeName, FileDefinitionModel definitionModel) {
        if (definitionModel.getCustomDataTypeMap().containsKey(typeName)) {
            return definitionModel.getCustomDataTypeMap().get(typeName)
                    .stream()
                    .map(String::new)
                    .collect(Collectors.toCollection(ArrayList::new));
        } else
            throw new ModGenerationServiceException(String.format("Failed to load custom data type for type '%s'", typeName));
    }


    private void _createScriptBlock(String fileName, ScriptModel scriptModel, ScriptBlock scriptBlock) {
        if (fileName.equals(scriptModel.getFileName())) {
            scriptModel.getItems().add(scriptBlock);
        }
    }

    public void removeScriptBlock(String modName, String version, String fileName, String scriptBlockName) {
        iterateScriptBlock(modName, version, scriptModel -> _removeScriptBlock(fileName, scriptBlockName, scriptModel));
    }

    private void iterateScriptBlock(String modName, String version, Consumer<? super ScriptModel> action) {
        ModPackageModel modPackageModel = runtimeDataService.getByType(ModPackageModel.class);
        modPackageModel.getModList().forEach(mod -> {
            if (mod.getModName().equals(modName)) {
                if (version.equals(ModDataEnum.COMMON.getValue())) {
                    mod.getCommon().getScriptFiles().forEach(action);
                } else {
                    mod.getModVersionMap().get(version).getScriptFiles().forEach(action);
                }
            }
        });
    }

    private void _removeScriptBlock(String fileName, String scriptBlockName, ScriptModel scriptModel) {
        if (fileName.equals(scriptModel.getFileName())) {
            scriptModel.getItems().removeIf(scriptItem -> scriptItem.getName().equals(scriptBlockName));
        }
    }

    private void init() {
        fileFactoryMap.put(ModDataKey.Script, new ScriptFactory());
        fileFactoryMap.put(ModDataKey.Animation, new AnimationFactory());
        fileFactoryMap.put(ModDataKey.Model_X, new ModelXFactory());
        fileFactoryMap.put(ModDataKey.Sandbox, new SandboxFactory());
        fileFactoryMap.put(ModDataKey.Lua, new LuaFactory());
        fileFactoryMap.put(ModDataKey.Sound, new SoundFactory());
        fileFactoryMap.put(ModDataKey.Texture, new TextureFactory());
        fileFactoryMap.put(ModDataKey.Translation, new TranslationFactory());
    }

    public ModDefinitionModel getModDefinitionModel(String version) {
        return getDefinitionModelByVersion(version);
    }

    private ModDefinitionModel getDefinitionModelByVersion(String version){
        DefinitionVersionMap versionMap = runtimeDataService.getByType(DefinitionVersionMap.class);

        if (versionMap.getMap().containsKey(version)) {
            return versionMap.getMap().get(version);
        }
        else if (version.equals(ModDataEnum.COMMON.getValue())) {
            return versionMap.getMap().get(VersionUtils.getOldestVersion(versionMap.getVersionList()));
        }
        else
            throw new ModGenerationServiceException(String.format("Failed to load definition model for version '%s'", version));
    }

    @Override
    public <T> void onEvent(Event<T> event) {
        if (event instanceof ReloadEvent){
            cachedScriptBlockTypes = null;
        }
    }
}
