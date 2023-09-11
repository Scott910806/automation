package org.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyDemo {
    public static void main(String[] args) {
        // 动态代理
        Hello hello = (Hello) Proxy.newProxyInstance(Hello.class.getClassLoader(),
                new Class[]{Hello.class},
                new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                System.out.println(method);
                if (method.getName().equals("morning")){
                    System.out.println("Good morning " + args[0]);
                }
                if (method.getName().equals("sayHi")){
                    System.out.println("Hello " + args[0]);
                }
                return null;
            }
        });
        hello.morning("scott");
        hello.sayHi("rock");
    }
}

class Student extends Person{
    public int score;
    private int grade;
    public int getScore(String type){
        return 99;
    }
    private int getGrade(int year){
        return 1;
    }
    public void hello(){
        System.out.println("Student::hello");
    }
}
class Person{
    public String name;
    public String getName(){
        return "Person";
    }
    public void hello(){
        System.out.println("Person::hello");
    }
}

interface Hello{
    void morning(String name);
    void sayHi(String name);
}
