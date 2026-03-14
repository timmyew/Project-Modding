package de.projectmodding.core.service;

import de.projectmodding.core.component.factory.file.*;
import de.projectmodding.core.component.factory.file.scriptblock.ScriptBlockFactory;
import de.projectmodding.core.constant.mod.ModFolderConstants;
import de.projectmodding.core.enums.ModDataKey;
import de.projectmodding.core.enums.ParameterAction;
import de.projectmodding.core.exception.ModGenerationServiceException;
import de.projectmodding.core.model.AttributeModel;
import de.projectmodding.core.model.definition.DefinitionVersionMap;
import de.projectmodding.core.model.definition.ModDefinitionModel;
import de.projectmodding.core.model.mod.ModData;
import de.projectmodding.core.model.mod.ModPackageModel;
import de.projectmodding.core.model.mod.files.*;
import de.projectmodding.core.model.mod.files.data.ScriptBlock;
import de.projectmodding.core.util.MathUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public final class ModDataGenerationService {
    private final HashMap<ModDataKey, IFactory<? extends BaseFile>> fileFactoryMap = new HashMap<>();
    private final RuntimeDataService runtimeDataService;

    public ModDataGenerationService(RuntimeDataService runtimeDataService) {
        this.runtimeDataService = runtimeDataService;
        init();
    }

    public WorkshopModel generateMod() {
        return null;
    }

    public BaseFile createFile(ModPackageModel modPackageModel, String modName, ModDataKey key, String version) {
        ModDefinitionModel definition = runtimeDataService.getByType(DefinitionVersionMap.class).getMap().get(version);

        if (fileFactoryMap.containsKey(key)) {
            final BaseFile baseFile = fileFactoryMap.get(key).create(definition, modPackageModel.getRootPath());

            modPackageModel.getModList().forEach(mod -> {
                if (mod.getModName().equals(modName)){
                    ModData modData = version.equals(ModFolderConstants.COMMON_FOLDER) ? mod.getCommon() : mod.getModVersionMap().get(version);
                    String newFileName = "";

                    switch(key){
                        case Animation:
                            newFileName = baseFile.getFileName().concat(String.valueOf(getUniqueNumber(modData.getAnimationFiles())));
                            modData.getAnimationFiles().add((AnimationModel)  baseFile);
                            break;
                        case Model_X:
                            newFileName = baseFile.getFileName().concat(String.valueOf(getUniqueNumber(modData.getModel_X_files())));
                            modData.getModel_X_files().add((Model_X_Model)  baseFile);
                            break;
                        case Sandbox:
                            newFileName = baseFile.getFileName().concat(String.valueOf(getUniqueNumber(modData.getSandboxFiles())));
                            modData.getSandboxFiles().add((SandboxModel)  baseFile);
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
        }
        else{
            throw new ModGenerationServiceException(String.format("Failed to generate mod data for key '%s'",key.toString()));
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

    public List<ScriptBlock> searchAndGetScriptBlocks(String modName, String modVersion, String fileName){
        AtomicReference<List<ScriptBlock>> scriptBlocks = new AtomicReference<>();
        ModPackageModel modPackageModel = runtimeDataService.getByType(ModPackageModel.class);

        modPackageModel.getModList().forEach(mod -> {
            if (mod.getModName().equals(modName)) {
                List<ScriptModel> scriptModels = null;
                if (modVersion.equals(ModFolderConstants.COMMON_FOLDER)){
                    scriptModels = mod.getCommon().getScriptFiles();
                }
                else{
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
        AtomicReference<ScriptBlock> scriptBlock = new AtomicReference<>(ScriptBlockFactory.createScriptBlock(name));
        iterateScriptBlock(modName, version, scriptModel -> _createScriptBlock(fileName, scriptModel, scriptBlock.get()));
        return scriptBlock.get();
    }

    public HashMap<String, List<String>> loadScriptBlockTypes(String version){
        ModDefinitionModel modDefinition = runtimeDataService.getByType(DefinitionVersionMap.class).getMap().get(version);
        HashMap<String, List<String>> scriptBlockTypesMap = new HashMap<>();

        modDefinition.getScript().getCustomTypeMappings().forEach(mapper -> {
            String parent = mapper.getParent();
            String child = mapper.getChild();

            if (scriptBlockTypesMap.containsKey(parent)) {
                scriptBlockTypesMap.get(parent).add(child);
            }
            else {
                ArrayList<String> list = new ArrayList<>();
                list.add(child);
                scriptBlockTypesMap.put(parent, list);
            }
        });

        return scriptBlockTypesMap;
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
                if (version.equals(ModFolderConstants.COMMON_FOLDER)){
                    mod.getCommon().getScriptFiles().forEach(action);
                }
                else{
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

    private void init(){
        fileFactoryMap.put(ModDataKey.Script, new ScriptFactory());
        fileFactoryMap.put(ModDataKey.Animation, new AnimationFactory());
        fileFactoryMap.put(ModDataKey.Model_X, new ModelXFactory());
        fileFactoryMap.put(ModDataKey.Sandbox, new SandboxFactory());
        fileFactoryMap.put(ModDataKey.Lua, new LuaFactory());
        fileFactoryMap.put(ModDataKey.Sound, new SoundFactory());
        fileFactoryMap.put(ModDataKey.Texture, new TextureFactory());
        fileFactoryMap.put(ModDataKey.Translation, new TranslationFactory());
    }
}
