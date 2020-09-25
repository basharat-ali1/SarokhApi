package net.sarokh.api.util;

import net.sarokh.api.model.DateTimeDTO;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    public static Date toDateFromString(String dateInput){

        String pattern = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'";

        if (ApplicationUtil.isNotNullAndEmpty(dateInput)) {

            if (dateInput.indexOf('t') > 0 && dateInput.indexOf('z') > 0) {
                pattern = "yyyy-MM-dd't'HH:mm:ss.sss'z'";
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            try {
                Date date = simpleDateFormat.parse(dateInput);
                System.out.println("Util Parsed Date=" + date);
                return date;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
/*
    public static String toUiDateFromString(String dateInput){

        String pattern = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'";

        if (ApplicationUtil.isNotNullAndEmpty(dateInput)) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            try {
                Date date = simpleDateFormat.parse(dateInput);
                System.out.println("Util Parsed Date=" + date.fo);
                return date;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
*/
    public static Date toDateFromSQLString(String dateInput){

        String pattern = "yyyy-MM-dd HH:mm:ss.0";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        try {
            Date date = simpleDateFormat.parse(dateInput);
            System.out.println("Sql Parsed Date=" + date);
            return date;
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static String convertToSqlSearchDate(Date date){
        System.out.println("DateTimeDTO Input Date: " + date);

        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();

        String sqlDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println("Converted SQL Date : " + sqlDate );

        return sqlDate ;
    }

    public static DateTimeDTO convertToDateAndTime(Date date){
        return convertToDateAndTime(date, "dd-MM-yyyy");
    }

    public static DateTimeDTO convertToDateAndTime(Date date, String dateFormat){
        // dateFormat = "dd-MM-yyyy"
        System.out.println("DateTimeDTO Input Date: " + date);
        if (date != null) {
            ZoneId defaultZoneId = ZoneId.systemDefault();
            Instant instant = date.toInstant();
            LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
            LocalTime time = instant.atZone(defaultZoneId).toLocalTime();

            System.out.println("Converted Date : " + localDate + ", Time : " + time);

            return DateTimeDTO.builder()
                    .date(localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                    .time(time.toString())
                    .build();
        }

        return null;
    }


    public static void main(String[] arg){

        String dateInString = "2020-07-06T19:00:00.000Z";

        try {
            Date date = toDateFromString(dateInString);

           // DateTimeDTO dateTimeDTO = convertToDateAndTime(date);
            convertToSqlSearchDate(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
