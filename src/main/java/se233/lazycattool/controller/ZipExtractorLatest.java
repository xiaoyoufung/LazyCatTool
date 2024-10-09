package se233.lazycattool.controller;

import se233.lazycattool.Launcher;
import se233.lazycattool.model.ImageFile;
import se233.lazycattool.model.FileType;
import se233.lazycattool.exception.ZipExtractionException;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.zip.*;

public class ZipExtractorLatest {

    public ZipExtractorLatest() {}

    public void extractZip(String zipFilePath) throws ZipExtractionException {
        Path tempDir = null;
        ArrayList<ImageFile> allUploadedImages = Launcher.getAllUploadedImages();

        try {
            tempDir = Files.createTempDirectory("tempImageDir");
            extractZipContent(zipFilePath, tempDir, allUploadedImages);

            Launcher.resetCropSetting(allUploadedImages);
            Launcher.setAllUploadedImages(allUploadedImages);
            Launcher.refreshPane();

            registerShutdownHook(tempDir);
        } catch (IOException | SecurityException | UnsupportedOperationException e) {
            throw new ZipExtractionException("Failed to extract zip file: " + e.getMessage(), e);
        }
    }

    private void extractZipContent(String zipFilePath, Path tempDir, ArrayList<ImageFile> allUploadedImages) throws IOException, ZipExtractionException {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().startsWith("__MACOSX") || entry.getName().startsWith("._")) {
                    continue;
                }

                Path filePath = tempDir.resolve(entry.getName());

                if (entry.isDirectory()) {
                    Files.createDirectories(filePath);
                } else {
                    extractFile(zis, filePath, allUploadedImages);
                }
            }
        } catch (ZipException e) {
            throw new ZipExtractionException("Invalid zip file: " + e.getMessage(), e);
        }
    }

    private void extractFile(ZipInputStream zis, Path filePath, ArrayList<ImageFile> allUploadedImages) throws IOException {
        Files.createDirectories(filePath.getParent());
        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(filePath))) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = zis.read(buffer)) > 0) {
                bos.write(buffer, 0, len);
            }
        }

        String imageName = filePath.getFileName().toString();
        if (!imageName.equals(".DS_Store")) {
            double imageSize = Files.size(filePath) / 1024.0;
            FileType imageType = imageName.endsWith(".png") ? FileType.png : FileType.jpg;
            allUploadedImages.add(new ImageFile(imageName, filePath.toString(), imageSize, imageType));
        }
    }

    private void registerShutdownHook(Path tempDir) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Files.walk(tempDir)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
                System.out.println("Temporary files deleted.");
            } catch (IOException e) {
                System.err.println("Failed to delete temporary files: " + e.getMessage());
            }
        }));
    }
}
