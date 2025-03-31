package org.spring;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        String filePath="C:/Users/Иван/Desktop/UserActions.java";
        BufferedReader reader;
        List<String> textLines=new ArrayList<>();

        try{

            reader=new BufferedReader(new FileReader(filePath));
            textLines.add(reader.readLine());
        } catch (IOException e){

            System.out.println(e.getMessage());
        }

        if(textLines.contains(".calendarSetDate("))

        try(FileWriter writer=new FileWriter(filePath,false)){

        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}