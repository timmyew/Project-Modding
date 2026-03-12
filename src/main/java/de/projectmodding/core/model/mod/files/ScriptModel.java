package de.projectmodding.core.model.mod.files;

import de.projectmodding.core.model.mod.files.data.ScriptBlock;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder()
public class ScriptModel extends BaseFile{
    String moduleName;
    List<ScriptBlock> items;
}
