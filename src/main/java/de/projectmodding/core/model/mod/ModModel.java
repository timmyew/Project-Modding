package de.projectmodding.core.model.mod;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
public class ModModel {
    String modName;
    HashMap<String, ModData> modVersionMap;
    ModData common;
}
