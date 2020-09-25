package net.sarokh.api.util;

public class Test {

    public static void main(String[] arg){
        String value = "00010000018";
        int length = value.length();
        System.out.println(length);
        Integer intValue = Integer.parseInt(value) + 1;
        System.out.println(intValue);

        String newId = "" + intValue;
        while (newId.length() < value.length()){
            newId = "0" + newId;
        }

        System.out.println(newId + "-" + newId.length());
    }
}
