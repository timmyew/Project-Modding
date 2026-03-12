package de.projectmodding.core.model.mod;

import de.projectmodding.core.model.mod.files.WorkshopModel;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ModPackageModel {
    String packetName;
    String rootPath;
    WorkshopModel workshop;
    List<ModModel> modList;
}
