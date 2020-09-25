package net.sarokh.api.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QRCodeGenerator {
    private static final String QR_CODE_IMAGE_PATH = "./MyQRCode.png";

    private static String generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        return path.getFileName().toString();
    }

    public static String generateQRCode(String text, String filePath){
        String fileName = null;

        try {
            fileName = generateQRCodeImage(text, 500, 500, filePath);
            System.out.println("QR Code generated: " + fileName);
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }

        return fileName;
    }

    public static void main(String[] args) {
        generateQRCode("Shipper1-01-2", QR_CODE_IMAGE_PATH);
    }
}
