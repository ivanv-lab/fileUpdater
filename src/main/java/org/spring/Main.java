package org.spring;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    static String filePath;
    static File files;
    static String[] filePaths;

    public static void main(String[] args) throws ClassNotFoundException {

        filePath="src/test/java/core";
        files=new File(filePath);
        filePaths=files.list();

        System.out.println("Hello world!");
        Arrays.stream(filePaths).forEach(f->System.out.println(f));

        Arrays.stream(filePaths).forEach(f->{
            List<String> fileLines=new ArrayList<>();
            try(FileReader fr=new FileReader(filePath+"/"+f); BufferedReader br=new BufferedReader(fr)){
                String line;
                while ((line=br.readLine())!=null){
                    fileLines.add(line);
                }
                br.close();
                fr.close();
            } catch (IOException e){
                System.out.println(e.getMessage());
            }

            for(int i=0;i<fileLines.size()-1;i++){
                String l=fileLines.get(i);
                if(l.contains(".calendarSetDate") &&
                        !fileLines.get(i+1).contains(".calendarSetTime") &&
                !fileLines.get(i+1).contains("calendarSave")){
                    fileLines.remove(i);
                    fileLines.add(i,l+".calendarSave()");
                }
            }

            for(String l:fileLines){
                System.out.println(l);
            }

            System.out.println();
            System.out.println("zzzzzzz");
        });
    }
}