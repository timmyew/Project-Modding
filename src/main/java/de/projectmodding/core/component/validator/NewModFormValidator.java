package de.projectmodding.core.component.validator;

import de.projectmodding.core.exception.BlankStringException;
import de.projectmodding.core.exception.InvalidPathException;
import de.projectmodding.core.util.FolderUtils;

public final class NewModFormValidator {
    private NewModFormValidator(){}

    public static void validate(String packageName, String rootPath) {
        if (packageName == null || packageName.isBlank()) {
            throw new BlankStringException("Package name cannot be empty!");
        }

        if (rootPath == null || rootPath.isBlank()) {
            throw new InvalidPathException("Root path cannot be empty!");
        }
        else if (!FolderUtils.isPathExists(rootPath)) {
            throw new InvalidPathException(String.format("Root path does not exist! \n Path: '%s'", rootPath));
        }
    }
}
