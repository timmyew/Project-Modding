package de.projectmodding.core.model.mod.files;

import de.projectmodding.core.enums.ModDataEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder()
public class SandboxModel extends BaseFile{
    @Override
    public ModDataEnum getFileType() {
        return ModDataEnum.SANDBOX;
    }
}
