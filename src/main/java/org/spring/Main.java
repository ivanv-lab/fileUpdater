package org.spring;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class Main {
    static String filePath;
    static File files;
    static String[] filePaths;
    static List<Class> classes;
    static List<Method> methods;

    public static void main(String[] args) throws ClassNotFoundException {

        filePath="src/test/java/core";
        files=new File(filePath);
        filePaths=files.list();

        System.out.println("Hello world!");
        Arrays.stream(filePaths).forEach(f->System.out.println(f));

        classes.add(Class.forName(Arrays.toString(filePaths)));
        methods.add(classes.stream().forEach(c->c.getMethods()))

    }
}