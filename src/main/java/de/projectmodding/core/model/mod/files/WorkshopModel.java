package de.projectmodding.core.model.mod.files;

import de.projectmodding.core.model.AttributeModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder()
public class WorkshopModel extends BaseFile {
    List<AttributeModel> attributes;
}
