package de.projectmodding.core.model.event;

import de.projectmodding.core.model.mod.files.data.ScriptBlock;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class FillItemEventModel {
    List<ScriptBlock> scriptBlocks;
    String modName;
    String modVersion;
    String fileName;
}
