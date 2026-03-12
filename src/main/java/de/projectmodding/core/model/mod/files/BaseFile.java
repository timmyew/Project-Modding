package de.projectmodding.core.model.mod.files;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder()
public class BaseFile {
    String fileName;
    String relatedPath;
}
