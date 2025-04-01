package org.spring;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
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

            try {
                //Files.createFile(Path.of("src/test/java/newtest/" + f));
                File file=new File("src/test/java/newtest/" + f);
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try{
                FileWriter writer=new FileWriter("src/test/java/newtest/" + f);
                for(String n:fileLines){
                    writer.append(n+"\n");
                }
                writer.close();
            } catch (IOException ioException){
                System.out.println(ioException.getMessage());
            }

//            try(FileWriter fw=new FileWriter("src/test/java/newtest/"+f,true)){
//                Files.createFile(Path.of("src/test/java/newtest/" + f));
//                for(String l:fileLines){
//                    System.out.println(l);
//                }
//            } catch (IOException e){
//                System.out.println(e.getMessage());
//            }
        });
    }
}