package net.sarokh.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageUtils {
    public static void main(String[] args) {
        String filePath = "/home/emumba/Documents/abc.png";
        System.out.println("=================Encoder File to Base 64!=================");
        String base64FileString = encoder(filePath);
        System.out.println("Base64FileString = " + base64FileString);

        System.out.println("=================Decoder Base64FileString to File!=================");
        decoder(base64FileString, "/home/emumba/Documents/xyz.png");

        System.out.println("DONE!");

    }

    public static String encoder(String filePath) {
        String base64File = "";
        File file = new File(filePath);
        try (FileInputStream fileInFile = new FileInputStream(file)) {
            // Reading a File file from file system
            byte[] fileData = new byte[(int) file.length()];
            fileInFile.read(fileData);
            base64File = Base64.getEncoder().encodeToString(fileData);
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the File " + ioe);
        }
        return base64File;
    }

    public static void decoder(String base64File, String pathFile) {
        try (FileOutputStream fileOutFile = new FileOutputStream(pathFile)) {
            // Converting a Base64 String into File byte array
            byte[] fileByteArray = Base64.getDecoder().decode(base64File);
            fileOutFile.write(fileByteArray);
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the File " + ioe);
        }
    }
}
