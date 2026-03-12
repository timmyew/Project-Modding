package de.projectmodding.core.util;

import de.projectmodding.core.exception.FileUtilsException;
import de.projectmodding.core.exception.InvalidPathException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileUtils {

    public static JSONObject readJson(String path) throws FileNotFoundException {
        JSONObject json;
        File file = new File(path);

        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            try {
                json = new JSONObject(reader.readAllAsString());
            } catch (Exception exception) {
                throw new JSONException(
                        String.format("An error occured by reading a json file with path: '%s' \n With exception: %s",
                                path, exception.getMessage()));
            }
        } else
            throw new FileNotFoundException(String.format("File with path '%s' not found.", path));

        return json;
    }

    public static <T> T readJson(String path, Class<T> clazz) throws FileNotFoundException {
        return readJson(path).fromJson(clazz);
    }

    public static void writeFile(String path, String fileName, String data) {
        if (!FolderUtils.isPathExists(path))
            throw new InvalidPathException(
                    String.format("A exception occurred in FileUtils. Can't write a file to a not existing path '%s'", path)
            );

        Path filePath = Path.of(path.concat(File.separator.concat(fileName)));

        try {
            Files.writeString(filePath, data);
        } catch (Exception exception) {
            throw new FileUtilsException(
                    String.format("An exception occurred while writing the file '%s' \n Exception message: %s",
                            filePath, exception.getMessage()
                    )
            );
        }
    }
}
