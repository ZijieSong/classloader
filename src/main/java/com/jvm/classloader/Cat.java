package com.jvm.classloader;

public class Cat {
    public Cat(Class clazz) throws IllegalAccessException, InstantiationException {
        System.out.println("im cat, my classloader is " + this.getClass().getClassLoader());
        Object o = clazz.newInstance();
        System.out.println("obj classloader is" + clazz.getClassLoader());
        System.out.println(o);
    }
}
