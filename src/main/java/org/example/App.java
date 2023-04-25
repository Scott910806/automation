package org.example;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        List<String> list = List.of("we,  are;;  second ;  to     none".split("[\\,\\;\\s]+"));
        for (String s : list) {
            System.out.println(s);
        }
        boolean bool = "L  A".matches("L\\s{2}A");
        System.out.println(bool);

         String p_n = "黄宏贵";
         String p_u = "fa3d363c-426f-4575-ab34-d3bf45755df5";
         String baseUrl = "http://hengine.apps01.ali-bj-sit03.shuheo.net/hengine";
        System.out.println(baseUrl + "?p_u=" + p_u + "&p_n=" + p_n);
    }

}
