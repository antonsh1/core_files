package classes;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Files {
    private final static String rootFolder = "C:\\Games\\";
    private static List<String> logInfo = new ArrayList<>();
    private static final String logFile = rootFolder + "\\temp\\temp.txt";

    public static final String saveGameFolder = rootFolder + "\\savegames\\";
    private static final Map<String, List<String>> listFilesFolders = new HashMap<>() {{
        put("src", Arrays.asList("main", "test"));
        put("res", Arrays.asList("Main.java", "Utils.java"));
        put("savegames", Arrays.asList("drawables", "vectors", "icons"));
        put("temp", List.of("temp.txt"));
    }};

    private static void CreateFolder(File object) {
        if (!object.exists()) {
            try {
                if (object.mkdir())
                    logInfo.add("Каталог создан " + object);
            } catch (SecurityException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        } else {
            logInfo.add("Каталог уже существует " + object);
        }
    }

    private static void CreateFile(File object) throws IOException {
        if (!object.exists()) {
            try {
                if (object.createNewFile())
                    logInfo.add("Файл создан " + object);
            } catch (SecurityException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        } else {
            logInfo.add("Файл уже существует " + object);
        }
    }

    private static void saveInfoToLog() {
        try (FileWriter writer = new FileWriter(logFile, false)) {
            for (String item : logInfo) {
                writer.write(item);
                writer.append('\n');
            }
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void MakeFileStructure() throws IOException {
        if(new File(rootFolder).exists()) {
            for (String entry : listFilesFolders.keySet()) {
                File newDir = new File(Files.rootFolder, entry);
                CreateFolder(newDir);
                for (String object : Files.listFilesFolders.get(entry)) {
                    if (object.contains(".")) {
                        CreateFile(new File(newDir, object));
                    } else {
                        CreateFolder(new File(newDir, object));
                    }
                }
            }
            saveInfoToLog();
        } else {
            throw new RuntimeException("Отсутствует корневая папка " + rootFolder);
        }

    }

    public static void zipFiles(String archiveFileName, String... datFiles) {
        try (ZipOutputStream zipOut = new ZipOutputStream(new
                FileOutputStream(saveGameFolder + archiveFileName))) {
            for (String fileName : datFiles) {
                File fileToZip = new File(saveGameFolder + fileName);
                if (fileToZip.exists()) {
                    ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                    zipOut.putNextEntry(zipEntry);
                    try (FileInputStream fis = new FileInputStream(fileToZip)) {
                        // считываем содержимое файла в массив byte
                        byte[] bytes = new byte[fis.available()];
                        int length;
                        while ((length = fis.read(bytes)) >= 0) {
                            zipOut.write(bytes, 0, length);
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    fileToZip.delete();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static List<File> UnZipFiles(String archiveName) {
        List<File> outputFiles = new ArrayList<>();
        try (ZipInputStream zipIn = new ZipInputStream(new
                FileInputStream(saveGameFolder + archiveName))) {
            ZipEntry zipEntry = zipIn.getNextEntry();
            while (zipEntry != null) {
                File newFile = new File(saveGameFolder, zipEntry.getName());
                try (FileOutputStream fOut = new FileOutputStream(newFile)) {
                    for (int c = zipIn.read(); c != -1; c = zipIn.read()) {
                        fOut.write(c);
                    }
                    fOut.flush();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                outputFiles.add(newFile);
                zipEntry = zipIn.getNextEntry();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return outputFiles;
    }
}
