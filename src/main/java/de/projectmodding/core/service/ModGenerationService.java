package de.projectmodding.core.service;

import de.projectmodding.core.component.factory.file.*;
import de.projectmodding.core.enums.ModDataKey;
import de.projectmodding.core.exception.ModGenerationServiceException;
import de.projectmodding.core.model.definition.ModDefinitionModel;
import de.projectmodding.core.model.mod.ModData;
import de.projectmodding.core.model.mod.ModPackageModel;
import de.projectmodding.core.model.mod.files.*;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public final class ModGenerationService {
    HashMap<ModDataKey, IFactory<? extends BaseFile>> fileFactoryMap = new HashMap<>();
    private static final String COMMON_FOLDER = "common";

    public ModGenerationService(){
        init();
    }

    public WorkshopModel generateMod() {
        return null;
    }

    public BaseFile createData(ModDefinitionModel definition, ModPackageModel modPackageModel, String modName, ModDataKey key, String version) {
        final AtomicReference<String> atomicModName = new AtomicReference<>(modName);
        final AtomicReference<String> atomicVersion = new AtomicReference<>(version);
        final AtomicReference<ModDataKey> atomicFactoryKey = new AtomicReference<>(key);

        if (fileFactoryMap.containsKey(atomicFactoryKey.get())) {
            final AtomicReference<BaseFile> atomicBaseFile = new AtomicReference<>(fileFactoryMap.get(key).create(definition, modPackageModel.getRootPath()));
            modPackageModel.getModList().forEach(mod -> {
                if (mod.getModName().equals(atomicModName.get())){
                    ModData modData = atomicVersion.get().equals(COMMON_FOLDER) ? mod.getCommon() : mod.getModVersionMap().get(atomicVersion.get());

                    switch(atomicFactoryKey.get()){
                        case Animation:
                            modData.getAnimationFiles().add((AnimationModel)  atomicBaseFile.get());
                            break;
                        case Model_X:
                            modData.getModel_X_files().add((Model_X_Model)  atomicBaseFile.get());
                            break;
                        case Sandbox:
                            modData.getSandboxFiles().add((SandboxModel)  atomicBaseFile.get());
                            break;
                        case Script:
                            modData.getScriptFiles().add((ScriptModel) atomicBaseFile.get());
                            break;
                        case Lua:
                            modData.getLuaFiles().add((LuaModel) atomicBaseFile.get());
                            break;
                        case Sound:
                            modData.getSoundFiles().add((SoundModel) atomicBaseFile.get());
                            break;
                        case Texture:
                            modData.getTextureFiles().add((TextureModel) atomicBaseFile.get());
                            break;
                        case Translation:
                            modData.getTranslationFiles().add((TranslationModel) atomicBaseFile.get());
                            break;
                    }
                }
            });
            return atomicBaseFile.get();
        }
        else{
            throw new ModGenerationServiceException(String.format("Failed to generate mod data for key '%s'",key.toString()));
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
