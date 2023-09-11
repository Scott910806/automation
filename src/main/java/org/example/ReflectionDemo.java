package org.example;

import java.lang.reflect.Method;

public class ReflectionDemo {
    public static void main(String[] args) throws Exception{
        printClassInfo("".getClass());
        printClassInfo(String[].class);
        printClassInfo(Runnable.class);
        printClassInfo(int.class);
        printClassInfo(java.time.Month.class);

        // reflection on Method 遵循多态
        Student s = new Student();
        // 获取父类的hello方法
        Method m1 = Person.class.getMethod("hello");
        // 在子类对象上调用hello方法，调用的是被重写的hello方法
        m1.invoke(s);
        Class sc = s.getClass();
        Method m2 = sc.getMethod("getScore", String.class);
        System.out.println(m2.invoke(s, "scott"));
        Method m3 = sc.getDeclaredMethod("getGrade", int.class);
        System.out.println(m3.getReturnType());
        m3.setAccessible(true);
        System.out.println(m3.invoke(s, 2001));

        // 调用构造方法
        Person p =  Person.class.getDeclaredConstructor().newInstance();
        p.hello();

        // 获取继承关系
        Class i = Integer.class;
        Class n = i.getSuperclass();
        Class o = n.getSuperclass();
        System.out.println(n);
        System.out.println(o);
        for (Class cl : i.getInterfaces()){
            System.out.println(cl);
        }
    }
    // 打印Class的一些信息
    static void printClassInfo(Class cls){
        System.out.println("Class name: " + cls.getName());
        System.out.println("Simple name: " + cls.getSimpleName());
        if (cls.getPackage() != null){
            System.out.println("Package name: " + cls.getPackageName());
        }
        System.out.println("is interface: " + cls.isInterface());
        System.out.println("is enum: " + cls.isEnum());
        System.out.println("is array: " + cls.isArray());
        System.out.println("is primitive: " + cls.isPrimitive());
    }
}
