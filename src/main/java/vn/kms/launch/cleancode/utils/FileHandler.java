package vn.kms.launch.cleancode.utils;

import java.io.*;

public class FileHandler {
    public static String loadFileData(String dataPath) throws IOException {
        FileReader input = new FileReader(dataPath);
        try (BufferedReader reader = new BufferedReader(input)) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
            return content.toString();
        }
    }

    public static File openOrCreateFolder(String folderPath) {
        File outputFolder = new File(folderPath);
        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }
        return outputFolder;
    }

    public static Writer openFileWriter(File outputFolder, String fileName, String extension) throws IOException {
        return new FileWriter(new File(outputFolder, fileName + "." + extension));
    }
}
