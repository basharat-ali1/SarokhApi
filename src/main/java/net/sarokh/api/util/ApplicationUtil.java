package net.sarokh.api.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;

/**
 * @author Arshad Ali
 *
 */

public class ApplicationUtil {


    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final String START_TIME = "START_TIME";
    public static final String END_TIME = "END_TIME";
    public static final String UTF8_ENCODING = "UTF-8";

    public static int getDateDifferenceInYears(String date) {

        int yearDifference = -1;

        try {

            if (date != null) {

                date = date.replaceAll("^[a-zA-Z]", "!");
                //logger.info(date);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/mm/dd");
                Date compareDate = formatter.parse(date.trim());
                Calendar compareDateCalender = Calendar.getInstance();
                compareDateCalender.setTime(compareDate);

                Calendar currentDatecalendar = Calendar.getInstance();
                currentDatecalendar.setTime(new Date());

                yearDifference = currentDatecalendar.get(Calendar.YEAR) - compareDateCalender.get(Calendar.YEAR);

                //logger.info("Current year: " + currentDatecalendar.get(Calendar.YEAR) + ", Compare year: "
                //		+ compareDateCalender.get(Calendar.YEAR) + ", Differnce = " + yearDifference);
            }

        } catch (Exception e) {
            //logger.error(e);
        }

        return yearDifference;
    }

    public static String encryptPassword(String password) {
        try {
            CryptoUtils crypto = new CryptoUtils();
            try {
                return crypto.encrypt(password);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String generateUUID() {

        UUID uuid = Generators.timeBasedGenerator(EthernetAddress.constructMulticastAddress()).generate();
        String encodeUuid = getUrlEncodedString(uuid.toString());

        return encodeUuid;
    }

    public static String generateUUID(HttpServletRequest request, String url) {
        try {
            String ip = getClientIpAddr(request);
            String uuid = ip + "<-=->" + url;
            CryptoUtils crypto = new CryptoUtils();
            try {
                return crypto.encrypt(uuid);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        return ip;
    }

    public static String getUrlEncodedString(String input) {
        String encodedString = input;

        try {
            encodedString = URLEncoder.encode(input, UTF8_ENCODING);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encodedString;
    }

    public static String getUrlDecodedString(String input) {
        String decodedString = input;

        try {
            decodedString = URLDecoder.decode(input, UTF8_ENCODING);
        } catch (Exception e) {
            //logger.error(e);
        }

        return decodedString;
    }

    public static String getAuthenticUserCallId(String url, HttpServletRequest request) {

        String userCollectionId = generateUUID(request, url);

        return userCollectionId;
    }

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static boolean isNotNullAndEmpty(List<?> list) {

        return list != null && !list.isEmpty();
    }

    public static boolean isNotNullAndEmpty(String str) {

        return str != null && !str.isEmpty();
    }
/*
    public static String getFileFromUrl(String imageUrl) {

        InputStream inputStream = null;
        OutputStream outputStream = null;
        String fileName = ApplicationConstants.IMAGES_UPLOAD_DIR + "tempimage" + (new Random()).nextInt() + ".jpg";

        try {

            URL url = new URL(imageUrl);
            inputStream = url.openStream();
            outputStream = new FileOutputStream(fileName);

            byte[] buffer = new byte[2048];
            int length;

            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }

        } catch (Exception e) {
            //logger.error(e);

        } finally {
            try {

                inputStream.close();
                outputStream.close();

            } catch (IOException e) {
                //logger.error("Finally IOException :- " + e.getMessage());
            }
        }

        return fileName;
    }

    /**
     * @param date
     *            (format = Fri, 09 Nov 2018)
     *
     * @param type
     *            (values = START_TIME / END_TIME )
     *
     */

    public static long convertDateToUnixTimeStamp(String date, String type) {

        long unixTime = System.currentTimeMillis();

        try {

            if (type.equals(START_TIME)) {
                date += " 00:00:00 GMT";
            } else if (type.equals(END_TIME)) {
                date += " 23:59:59 GMT";
            }

            DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss z");
            Date newdate = dateFormat.parse(date);
            unixTime = newdate.getTime() / 1000;
            //logger.info(unixTime);

        } catch (Exception e) {
            //logger.error(e.getMessage());
        }

        return unixTime;
    }

}
