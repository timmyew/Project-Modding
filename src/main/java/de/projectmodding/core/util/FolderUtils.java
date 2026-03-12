package de.projectmodding.core.util;

import de.projectmodding.core.exception.FolderUtilsException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FolderUtils {
    private FolderUtils() {
    }

    public static void createFolder(String path) {
        try {
            if (!isPathExists(path))
                Files.createDirectories(Path.of(path));
        } catch (Exception exception) {
            throw new FolderUtilsException(
                    String.format("An exception occurred while creating a new directory in FolderUtils with path '%s'",
                            path)
            );
        }
    }

    public static String getWorkingDir() {
        return Paths.get("")
                .toAbsolutePath()
                .toString()
                .concat(File.separator);
    }

    public static boolean isPathExists(String path){
        return path != null && !path.isBlank() && Files.exists(Path.of(path));
    }
}
