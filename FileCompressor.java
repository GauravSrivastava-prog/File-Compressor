import java.io.*;
import java.util.zip.*;

public class FileCompressor {

    public static void compressFile(String sourceFilePath, String outputZipPath) {
        File sourceFile = new File(sourceFilePath);
        long originalSize = sourceFile.length();

        try (FileInputStream fis = new FileInputStream(sourceFile);
                FileOutputStream fos = new FileOutputStream(outputZipPath);
                ZipOutputStream zos = new ZipOutputStream(fos)) {

            ZipEntry zipEntry = new ZipEntry(sourceFile.getName());
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) >= 0) {
                zos.write(buffer, 0, length);
            }

            zos.closeEntry();

        } catch (IOException e) {
            e.printStackTrace();
        }

        File compressedFile = new File(outputZipPath);
        long compressedSize = compressedFile.length();
        double compressionRatio = ((double) (originalSize - compressedSize) / originalSize) * 100;

        System.out.printf("Original size: %d bytes%n", originalSize);
        System.out.printf("Compressed size: %d bytes%n", compressedSize);
        System.out.printf("Compression ratio: %.2f%%%n", compressionRatio);
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java FileCompressor <source file> <output zip file>");
            return;
        }

        String sourceFilePath = args[0];
        String outputZipPath = args[1];

        compressFile(sourceFilePath, outputZipPath);
    }
}