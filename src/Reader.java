
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedWriter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class Reader {
    
    private String fileName;
    private File file;
    FileReader fr;
    BufferedReader br;
    FileWriter fw;
    BufferedWriter bw;
    String origin = "";
    String manipulate = "";
    
    public Reader(String fileName){
        this.fileName = fileName;
        file = new File(fileName);
    }
    
    public void openReader(){
        try{
            fr = new FileReader(fileName);
        }
        catch(FileNotFoundException ex1){
            
            try{
                file.createNewFile();
                try{
                    fr = new FileReader(fileName);
                    System.out.println("New file created: " + fileName);
                }
                catch(FileNotFoundException e5){
                    System.out.println(e5);
                }
            }
            catch(IOException ex2){
                System.out.println(ex2.toString());
            }
        }
        br = new BufferedReader(fr);
    }
    
    public void openWriter(){
        try{
            fw = new FileWriter(fileName);
        }
        catch(IOException ex1){
            
            try{
                file.createNewFile();
                try{
                    fw = new FileWriter(fileName);
                    System.out.println("New file created: " + fileName);
                }
                catch(FileNotFoundException e5){
                    System.out.println(e5);
                }
            }
            catch(IOException ex2){
                System.out.println(ex2.toString());
            }
        }
        bw = new BufferedWriter(fw);
    }
    
    public String readAll(){
        try{
            manipulate = "";
            while((manipulate = br.readLine())!= null){
                origin = origin + manipulate + "\n";
            }
            return origin;
            

        }
        catch(IOException e3){
            System.out.println(e3);
            return null;
        }
    }
    
    public void writeAll(String origin){
        try{
            bw.write(origin);
        }
        catch(IOException e1){
            System.out.println(e1);
            try{
                bw.write("Failure");
            }
            catch(IOException e2){
                System.out.println(e2);
            }
        }
    }
    
    public void closeReader(){
        try{
            br.close();
        }
        catch(IOException e4){
            System.out.println(e4);
        }
    }
    
    public void closeWriter(){
        try{
            bw.close();
        }
        catch(IOException e1){
            System.out.println(e1);
        }
    }
    
    public void scanAll(String fileName){
        try{
            Scanner file = new Scanner(new File(fileName));
            while(file.hasNextLine()){
                String line = file.nextLine();
                origin += line + "\n";
            }
            file.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
}
