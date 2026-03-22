package de.projectmodding.core.model.mod.files;

import de.projectmodding.core.enums.ModDataEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder()
public abstract class BaseFile {
    public abstract ModDataEnum getFileType();
    String fileName;
    String relatedPath;
    String moduleName;
}
