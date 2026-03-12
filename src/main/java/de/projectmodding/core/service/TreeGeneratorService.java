package de.projectmodding.core.service;

import de.projectmodding.core.enums.ModDataKey;
import de.projectmodding.core.model.mod.ModData;
import de.projectmodding.core.model.mod.ModPackageModel;
import de.projectmodding.core.model.mod.files.*;
import de.projectmodding.gui.tree.node.ModPackageTreeNode;

import javax.swing.tree.DefaultTreeModel;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class TreeGeneratorService {
    private static TreeGeneratorService instance;
    private static final String MOD_FOLDER = "Mods";
    private static final String COMMON_FOLDER = "common";
    private static final String ANIMATION_FOLDER = "Animations";
    private static final String MODEL_X_FOLDER = "Model_X";
    private static final String SANDBOX_FOLDER = "Sandbox";
    private static final String SCRIPT_FOLDER = "Scripts";
    private static final String LUA_FOLDER = "Lua";
    private static final String SOUND_FOLDER = "Sounds";
    private static final String TEXTURE_FOLDER = "Texture";
    private static final String TRANSLATION_FOLDER = "Translation";

    public TreeGeneratorService() {}


    public DefaultTreeModel generateTreeModel(ModPackageModel packageModel) {
        ModPackageTreeNode root = new ModPackageTreeNode(packageModel.getPacketName());
        addWorkShop(root, packageModel);
        addMods(root, packageModel);

        return new DefaultTreeModel(root);
    }

    private void addWorkShop(ModPackageTreeNode root, ModPackageModel modPackage){
        root.add(new ModPackageTreeNode(modPackage.getWorkshop()));
    }

    private void addMods(ModPackageTreeNode root, ModPackageModel modPackage){
        AtomicReference<ModPackageTreeNode> atomicModFolder = new AtomicReference<>(new ModPackageTreeNode(MOD_FOLDER));

        modPackage.getModList().forEach(modModel -> {
            ModPackageTreeNode modNode = new ModPackageTreeNode(modModel.getModName());
            addModVersions(modNode, modModel.getModVersionMap(), modModel.getModName());
            addCommon(modNode, modModel.getCommon(), modModel.getModName());
            atomicModFolder.get().add(modNode);
        });

        root.add(atomicModFolder.get());
    }

    private void addModVersions(ModPackageTreeNode root, HashMap<String, ModData> modVersionMap, String modName){
        AtomicReference<ModPackageTreeNode> atomicRoot  = new AtomicReference<>(root);
        AtomicReference<String> atomicModName = new AtomicReference<>(modName);

        modVersionMap.forEach((version, value) -> {
            ModPackageTreeNode modVersion = new ModPackageTreeNode(version);
            addModData(modVersion, value, version, modName);
            atomicRoot.get().add(modVersion);
        });
    }

    private void addCommon(ModPackageTreeNode root, ModData modData, String modName){
        ModPackageTreeNode commonFolder = new ModPackageTreeNode(COMMON_FOLDER);
        addModData(commonFolder, modData, COMMON_FOLDER, modName);
        root.add(commonFolder);
    }

    private void addModData(ModPackageTreeNode root, ModData modData, String version, String modName){
        addAnimationModels(root, modData.getAnimationFiles(), version, modName);
        addModelXs(root, modData.getModel_X_files(), version, modName);
        addSandBoxModels(root, modData.getSandboxFiles(), version, modName);
        addScripts(root, modData.getScriptFiles(), version, modName);
        addLuaModels(root, modData.getLuaFiles(), version, modName);
        addSoundModels(root, modData.getSoundFiles(), version, modName);
        addTextureModels(root, modData.getTextureFiles(), version, modName);
        addTranslationModels(root, modData.getTranslationFiles(), version, modName);
        addModInfo(root, modData.getModInfoFile());
    }

    private void addAnimationModels(ModPackageTreeNode root, List<AnimationModel> animations, String version, String modName){
        ModPackageTreeNode animationFolder = new ModPackageTreeNode(ANIMATION_FOLDER, ModDataKey.Animation, version, modName);
        addBaseFiles(animationFolder, animations);
        root.add(animationFolder);
    }
    private void addModelXs(ModPackageTreeNode root, List<Model_X_Model> modelXModels, String version, String modName){
        ModPackageTreeNode modelXFolder = new ModPackageTreeNode(MODEL_X_FOLDER, ModDataKey.Model_X, version, modName);
        addBaseFiles(modelXFolder, modelXModels);
        root.add(modelXFolder);
    }
    private void addScripts(ModPackageTreeNode root, List<ScriptModel> scripts, String version, String modName){
        ModPackageTreeNode scriptFolder = new ModPackageTreeNode(SCRIPT_FOLDER, ModDataKey.Script, version, modName);
        root.add(scriptFolder);
        addBaseFiles(scriptFolder, scripts);
    }
    private void addLuaModels(ModPackageTreeNode root, List<LuaModel> luaModels, String version, String modName){
        ModPackageTreeNode luaFolder = new ModPackageTreeNode(LUA_FOLDER, ModDataKey.Lua, version, modName);
        addBaseFiles(luaFolder, luaModels);
        root.add(luaFolder);
    }
    private void addSandBoxModels(ModPackageTreeNode root, List<SandboxModel> sandboxes, String version, String modName){
        ModPackageTreeNode sandboxFolder = new ModPackageTreeNode(SANDBOX_FOLDER, ModDataKey.Sandbox, version, modName);
        addBaseFiles(sandboxFolder, sandboxes);
        root.add(sandboxFolder);
    }
    private void addSoundModels(ModPackageTreeNode root, List<SoundModel> soundModels, String version, String modName){
        ModPackageTreeNode soundFolder = new ModPackageTreeNode(SOUND_FOLDER, ModDataKey.Sound, version, modName);
        addBaseFiles(soundFolder, soundModels);
        root.add(soundFolder);
    }
    private void addTextureModels(ModPackageTreeNode root, List<TextureModel> textures, String version, String modName){
        ModPackageTreeNode textureFolder = new ModPackageTreeNode(TEXTURE_FOLDER, ModDataKey.Texture, version, modName);
        addBaseFiles(textureFolder, textures);
        root.add(textureFolder);
    }
    private void addTranslationModels(ModPackageTreeNode root, List<TranslationModel> translations, String version, String modName){
        ModPackageTreeNode translationFolder = new ModPackageTreeNode(TRANSLATION_FOLDER, ModDataKey.Translation, version, modName);
        addBaseFiles(translationFolder, translations);
        root.add(translationFolder);
    }

    private void addModInfo(ModPackageTreeNode root, ModInfoModel modInfo){
        root.add(new ModPackageTreeNode(modInfo));
    }

    private void addBaseFiles(ModPackageTreeNode root, List<?> baseFiles){
        AtomicReference<ModPackageTreeNode> atomicRoot = new AtomicReference<>(root);

        baseFiles.forEach(file -> {
            if (file instanceof BaseFile baseFile) {
                atomicRoot.get().add(new ModPackageTreeNode(baseFile));
            }
        });
    }
}
