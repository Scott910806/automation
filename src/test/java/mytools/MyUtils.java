package mytools;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class MyUtils {

    public static void main(String[] args) throws Exception{
        System.out.println(readJsonFile("src/test/java/data/demo.json"));
//        System.out.println(readFile("src/test/java/data/demo.json"));
    }
    /**
     * 读取文件
     * @param path 文件路径
     * @return 返回一个json字符串
     */
    public static String readJsonFile(String path) throws Exception {

        try(Reader reader = new FileReader(path, StandardCharsets.UTF_8)){
            char[] buffer = new char[1024];
            StringBuilder sb = new StringBuilder();
            int n;
            while ((n = reader.read(buffer)) != -1){
                sb.append(new String(buffer,0,n));
            }
            return sb.toString();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static String readFile(String path) throws Exception{
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))){
            StringBuilder sb = new StringBuilder();
            bufferedReader.lines().forEach(line -> sb.append(line));
            return sb.toString();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}

