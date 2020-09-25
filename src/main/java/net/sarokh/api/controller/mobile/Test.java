package net.sarokh.api.controller.mobile;

public class Test {
    public static void main(String[] args){
        String a = "0001";

        String[] array = a.split(",");
        System.out.println("length = " + array.length);

        a = "0002,00025";
        array = a.split(",");
        System.out.println("length = " + array.length);
    }
}
