package net.sarokh.api.util;

import java.io.File;
        import java.io.FileOutputStream;
import java.io.IOException;

import com.google.zxing.BarcodeFormat;
        import com.google.zxing.WriterException;
        import com.google.zxing.client.j2se.MatrixToImageWriter;
        import com.google.zxing.common.BitMatrix;
        import com.google.zxing.oned.Code128Writer;


public class BarCodeGenerator {

    private static void generateBarCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        BitMatrix bitMatrix = new Code128Writer().encode(text,BarcodeFormat.CODE_128,width,height,null);
        MatrixToImageWriter.writeToStream(bitMatrix, "png", new FileOutputStream(new File(filePath)));
    }

    public static String generateBarCode(String text, String path) {

        int width = 440;
        int height = 48;

        try {
             generateBarCodeImage(text, width, height , path);
        } catch (WriterException e) {
            path = null;
            e.printStackTrace();
        } catch (Exception e) {
            path = null;
            e.printStackTrace();
        }

        return path;
    }

    public static void main(String[] args) {
       String path = generateBarCode("Shipper1-5-2", "/home/emumba/Documents/Sarokh/barcode_files/Shipper1-5-2.png");
       System.out.println(path.replaceAll( "/home/emumba/Documents/Sarokh/", "http://test/"));
    }

}