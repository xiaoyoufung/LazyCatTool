package se233.lazycattool.controller;
import se233.lazycattool.Launcher;
import se233.lazycattool.model.ImageFile;
import se233.lazycattool.model.FileType;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipExtractor {

    public ZipExtractor(){}

    public void extractZip(String zipFilePath){
        Path tempDir = null;
        ArrayList<ImageFile> allUploadedImages = Launcher.getAllUploadedImages();

        //String zipFilePath = "/Users/xiaoyoufung/Desktop/test-photo.zip";

        try {
            // Create a temporary directory
            tempDir = Files.createTempDirectory("tempImageDir");

            // Extract the ZIP file
            try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {

                    // not get __MACOSX folder.
                    if (entry.getName().startsWith("__MACOSX") || entry.getName().startsWith("._")) {
                        continue;
                    }

                    Path filePath = tempDir.resolve(entry.getName());

                    if (entry.isDirectory()) {
                        Files.createDirectories(filePath);
                    } else {
                        Files.createDirectories(filePath.getParent());
                        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(filePath))) {
                            byte[] buffer = new byte[1024];
                            int len;
                            while ((len = zis.read(buffer)) > 0) {
                                bos.write(buffer, 0, len);
                            }
                        }

                        String imageName = filePath.getFileName().toString();
                        double imageSize = (Files.size(filePath) / 1024.0);
                        FileType imageType = imageName.endsWith(".png") ? FileType.png : FileType.jpg;

                        if (!imageName.equals(".DS_Store")){
                            allUploadedImages.add(new ImageFile(imageName, filePath.toString(), imageSize, imageType));
                        }
                    }
                }
            }

            Launcher.resetCropSetting(allUploadedImages);
            Launcher.setAllUploadedImages(allUploadedImages);
            Launcher.refreshPane();

            // Register a shutdown hook to delete the temporary directory on program termination
            Path finalTempDir = tempDir;
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    Files.walk(finalTempDir)
                            .sorted(Comparator.reverseOrder())
                            .map(Path::toFile)
                            .forEach(File::delete);
                    System.out.println("Temporary files deleted.");
                } catch (IOException e) { // (E.1) IOException
                    e.printStackTrace();
                }
            }));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
