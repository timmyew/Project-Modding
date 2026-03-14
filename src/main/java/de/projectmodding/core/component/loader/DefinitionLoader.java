package de.projectmodding.core.component.loader;

import de.projectmodding.core.component.processor.ModFolderStructureProcessor;
import de.projectmodding.core.component.validator.AttributeValidator;
import de.projectmodding.core.component.validator.DefinitionDetailModelValidator;
import de.projectmodding.core.constant.definition.AttributeConstants;
import de.projectmodding.core.constant.definition.DefinitionFileConstants;
import de.projectmodding.core.enums.DataType;
import de.projectmodding.core.exception.DefinitionLoaderException;
import de.projectmodding.core.model.AttributeModel;
import de.projectmodding.core.model.VersionListModel;
import de.projectmodding.core.model.definition.DefinitionVersionMap;
import de.projectmodding.core.model.definition.FileDefinitionModel;
import de.projectmodding.core.model.definition.ModDefinitionModel;
import de.projectmodding.core.util.FileUtils;
import de.projectmodding.core.util.FolderUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public final class DefinitionLoader {
    private final String basePath = FolderUtils.getWorkingDir();
    private static DefinitionLoader instance = null;

    private DefinitionLoader() {
    }

    public static DefinitionLoader getInstance() {
        if (instance == null) {
            instance = new DefinitionLoader();
        }
        return instance;
    }

    public DefinitionVersionMap load() {
        List<String> versionList = loadVersion().getVersionList();
        AtomicReference<HashMap<String, ModDefinitionModel>> versionDefinitionMap = new AtomicReference<>();
        versionDefinitionMap.set(new HashMap<>());

        versionList.forEach(version -> {
            String modinfo = basePath.concat(DefinitionFileConstants.MOD_INFO.replace(DefinitionFileConstants.VERSION_VAR, version));
            String workshop = basePath.concat(DefinitionFileConstants.WORKSHOP.replace(DefinitionFileConstants.VERSION_VAR, version));
            String script = basePath.concat(DefinitionFileConstants.SCRIPT.replace(DefinitionFileConstants.VERSION_VAR, version));
            String modFolder = basePath.concat(DefinitionFileConstants.MOD_FOLDER.replace(DefinitionFileConstants.VERSION_VAR, version));

            ModDefinitionModel model = ModDefinitionModel.builder()
                    .modInfo(loadAttributes(modinfo))
                    .workShop(loadAttributes(workshop))
                    .script(loadAttributes(script))
                    .modFolder(loadAttributes(modFolder))
                    .build();

            versionDefinitionMap.get().put(version, model);
        });

        DefinitionVersionMap definitionVersionMap = DefinitionVersionMap.builder()
                .map(versionDefinitionMap.get())
                .versionList(versionList)
                .build();

        ModFolderStructureProcessor.process(definitionVersionMap);
        return definitionVersionMap;
    }

    private VersionListModel loadVersion() {
        String versionFilePath = basePath.concat(DefinitionFileConstants.VERSION);

        try {
            return FileUtils.readJson(versionFilePath, VersionListModel.class);
        } catch (Exception exception) {
            throw new DefinitionLoaderException(
                    String.format("An exception occured by loading the Version.json definition file at '%s'", versionFilePath)
            );
        }
    }

    private FileDefinitionModel loadAttributes(String path) {
        try {
            AtomicReference<StringBuilder> contextBuilder = new AtomicReference<>(new StringBuilder(String.format("Loading attributes from '%s': \n ", path)));
            AtomicReference<List<AttributeModel>> attributeModels = new AtomicReference<>(new ArrayList<>());
            AtomicReference<HashMap<String, List<String>>> dataTypes = new AtomicReference<>(new HashMap<>());
            AtomicReference<String> filePath = new AtomicReference<>("");
            JSONObject json = loadJson(path);
            final AtomicReference<String> atomicPath = new AtomicReference<>(path);
            ArrayList<FileDefinitionModel.CustomTypeMappings> customTypeMapping = new ArrayList<>();

            json.names().forEach(key -> {
                Object object = json.get(key.toString());

                if (key.equals(AttributeConstants.ATTRIBUTE_LIST)) {
                    json.getJSONArray(AttributeConstants.ATTRIBUTE_LIST).iterator().forEachRemaining(obj -> {
                        JSONObject jsonObject = (JSONObject) obj;
                        AttributeValidator.validate(jsonObject, contextBuilder.get().toString());

                        processDatatype(jsonObject, AttributeConstants.DATA_TYPE);
                        processDatatype(jsonObject, AttributeConstants.SUB_TYPE);

                        AttributeModel attributeModel = JSONObject.fromJson(jsonObject.toString(), AttributeModel.class);

                        attributeModels.get().add(attributeModel);
                        contextBuilder.get().append(String.format("Attribute '%s' Validated!\n", attributeModel.getKeyName()));
                    });
                } else if (key.equals(AttributeConstants.FILE_PATH) && object instanceof String) {
                    if (!filePath.get().isBlank())
                        throw new DefinitionLoaderException(
                                String.format("An exception occurred while trying to load the file path. More then one filePath attribute in '%s'.", atomicPath.get())
                        );

                    filePath.set(object.toString());
                } else if (key.equals(AttributeConstants.CUSTOM_TYPE_MAPPINGS)) {
                    json.getJSONArray(key.toString()).forEach(obj -> {
                        customTypeMapping.add(JSONObject.fromJson(obj.toString(), FileDefinitionModel.CustomTypeMappings.class));
                    });
                } else if (object instanceof JSONArray) {
                    ArrayList<String> list = new ArrayList<>();
                    dataTypes.get().put(key.toString(), list);
                    json.getJSONArray(key.toString()).forEach(obj -> {
                        list.add(obj.toString());
                    });
                }
            });

            FileDefinitionModel result = FileDefinitionModel.builder()
                    .customDataTypeMap(dataTypes.get())
                    .attributes(attributeModels.get())
                    .filePath(filePath.get())
                    .customTypeMappings(customTypeMapping)
                    .build();

            DefinitionDetailModelValidator.validate(result);
            return result;
        } catch (Exception exception) {
            throw new DefinitionLoaderException(
                    String.format("Loading definition file with path '%s' failed: \n %s", path, exception.getMessage())
            );
        }
    }

    private void processDatatype(JSONObject jsonObject, String dataTypeFieldName) {
        if (jsonObject.has(dataTypeFieldName) &&
                !DataType.isEnum(jsonObject.getString(dataTypeFieldName))) {
            jsonObject.put(AttributeConstants.CUSTOM_TYPE, jsonObject.getString(dataTypeFieldName));
            jsonObject.put(dataTypeFieldName, DataType.Custom);
        }
    }

    private JSONObject loadJson(String path) {
        try {
            return FileUtils.readJson(path);
        } catch (Exception exception) {
            throw new DefinitionLoaderException(String.format("Failed to load attributes with path: '%s'", path));
        }
    }
}
