package jsamples;

public class MyStringUtils {
    static boolean isPalindrome(String str){
        return str.equals(new StringBuilder(str).reverse().toString());
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome("pop"));
        System.out.println(isPalindrome("hello"));
        System.out.println(isPalindrome("radar"));
    }
}
