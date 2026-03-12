package de.projectmodding.core.model.mod;

import de.projectmodding.core.model.mod.files.*;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ModData {
    ModInfoModel modInfoFile;
    ArrayList<AnimationModel> animationFiles;
    ArrayList<Model_X_Model> model_X_files;
    ArrayList<SandboxModel> sandboxFiles;
    ArrayList<ScriptModel> scriptFiles;
    ArrayList<LuaModel> luaFiles;
    ArrayList<SoundModel> soundFiles;
    ArrayList<TextureModel> textureFiles;
    ArrayList<TranslationModel> translationFiles;
    //ToDo: Images
    String poster;
    //ToDo: Images
    String icon;
}
