package net.sarokh.api.util;

import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SmsUtil {

    public static String sendSMS(RestTemplate restTemplate, String cellNumber, String msg) {

        /*
        The SMS API is not working with the proper way of sending params. That's why code is commented.

        String url = "https://www.enjazsms.com/api/sendsms.php";
        Map<String, String> params = new HashMap<>();
        params.put("username", "sarokh");
        params.put("password", "Sa123456");
        params.put("message", msg);
        params.put("sender", "SAROKH");
        params.put("numbers", cellNumber);
        params.put("return", "JSON");
        params.put("unicode", "E");
        params.put("sendtime", new Date().toString());
        params.put("offsetzone", "UTC+03:00");
        params.put("zonename", "AST");

        String response = restTemplate.getForObject(url, String.class, params);
        */

        if (ApplicationUtil.isNotNullAndEmpty(cellNumber)){
            if (cellNumber.indexOf(0) != '0' || cellNumber.indexOf(0) != '+'){
                cellNumber = "+" + cellNumber;
            }
        }

        String url1 = "https://www.enjazsms.com/api/sendsms.php?username=sarokh&password=Sa123456&message=" +
                            msg + "&numbers=" + cellNumber + "&sender=SAROKH&unicode=E&return=JSON";

        String response = restTemplate.getForObject(url1, String.class);

        System.out.println("Send SMS Response = " + response);

        return response;
    }

}
