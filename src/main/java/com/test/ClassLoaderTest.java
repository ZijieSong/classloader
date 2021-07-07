package com.test;

import com.jvm.classloader.Dog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

public class ClassLoaderTest extends ClassLoader {
    private String classLoaderName;
    /* 从什么地方去加载，这是个绝对路径 */
    private String path;

    private final String fileExtension = ".class";//要加载的字节码文件的扩展名

    public ClassLoaderTest(String classLoaderName) {
        super();//将系统类加载器当作该类加载器的父加载器
        this.classLoaderName = classLoaderName;
    }

    public ClassLoaderTest(ClassLoader parent, String classLoaderName) {
        super(parent);//显示指定该类加载器的父加载器
        this.classLoaderName = classLoaderName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "[" + classLoaderName + "]";
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        System.out.println("findClass invoked: " + className);
        System.out.println("class loader name: " + this.classLoaderName);
        byte[] data = this.loadClassData(className);
        return this.defineClass(className, data, 0, data.length);
    }

    private byte[] loadClassData(String className) {
        InputStream is = null;
        byte[] data = null;
        ByteArrayOutputStream baos = null;

        String[] split = className.split("\\.");
        String file = this.path + split[split.length - 1] + this.fileExtension;

        try {
            is = new FileInputStream(new File(file));
            baos = new ByteArrayOutputStream();

            int ch = 0;

            while (-1 != (ch = is.read())) {
                baos.write(ch);
            }

            data = baos.toByteArray();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                is.close();
                baos.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


        return data;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("main is " + ClassLoaderTest.class.getClassLoader());
        ClassLoaderTest loader1 = new ClassLoaderTest("loader1");
        loader1.setPath("/Users/szj/work/code/");
        Class<?> clazz = loader1.loadClass("com.jvm.classloader.Dog");
        System.out.println("class:" + clazz.hashCode());
        System.out.println("classloader:" + clazz.getClassLoader());
        Object object = clazz.newInstance();
        Method test = clazz.getMethod("test");
        test.invoke(object);

        System.out.println(Dog.class);

        System.out.println();
    }
}