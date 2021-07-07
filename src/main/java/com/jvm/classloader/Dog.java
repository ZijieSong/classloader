package com.jvm.classloader;

public class Dog {
    public Dog() {
        System.out.println("im dog, my classloader is " + this.getClass().getClassLoader());
    }
    public void test() throws InstantiationException, IllegalAccessException {
        new Cat(Dog.class);
    }
}
