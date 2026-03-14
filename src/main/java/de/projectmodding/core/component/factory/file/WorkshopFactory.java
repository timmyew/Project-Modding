package de.projectmodding.core.component.factory.file;

import de.projectmodding.core.constant.mod.ModFileNameConstants;
import de.projectmodding.core.enums.ParameterAction;
import de.projectmodding.core.model.AttributeModel;
import de.projectmodding.core.model.definition.ModDefinitionModel;
import de.projectmodding.core.model.mod.files.WorkshopModel;
import de.projectmodding.core.util.AttributeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class WorkshopFactory {
    private WorkshopFactory() {
    }

    public static WorkshopModel createDefaultWorkshopModel(ModDefinitionModel modDefinitionModel, String packageName, String packageVersion, String rootPath) {
        final AtomicReference<List<AttributeModel>> atomicWorkshopAttributes = new AtomicReference<>(new ArrayList<>());
        final AtomicReference<String> atomicPackageName = new AtomicReference<>(packageName);
        final AtomicReference<String> atomicPackageVersion = new AtomicReference<>(packageVersion);

        modDefinitionModel.getWorkShop().getAttributes().forEach(attribute -> {
            if (attribute.getRequired() != null && attribute.getRequired()) {
                AttributeModel newAttributeModel = attribute.toBuilder().build();
                atomicWorkshopAttributes.get().add(newAttributeModel);

                if (newAttributeModel.getAction() != null) {
                    switch (newAttributeModel.getAction()) {
                        case ParameterAction.Transform_Name:
                            newAttributeModel.setValue(atomicPackageName.get());
                            break;
                        case ParameterAction.Transform_Version:
                            newAttributeModel.setValue(atomicPackageVersion.get());
                            break;
                    }
                }
            }
        });


        return WorkshopModel.builder()
                .attributes(atomicWorkshopAttributes.get())
                .fileName(ModFileNameConstants.WORKSHOP)
                .relatedPath(rootPath.concat(File.separator).concat(
                                AttributeUtils.getAttribute(
                                        modDefinitionModel.getWorkShop().getFilePath(),
                                        modDefinitionModel.getModFolder().getAttributes()
                                ).getValue().replace(modDefinitionModel.getWorkShop().getFilePath(), packageName)
                        )
                )
                .build();
    }
}
