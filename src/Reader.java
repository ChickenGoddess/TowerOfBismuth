
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

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
    
    public String readAll(){
        try{
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
    
    public void closeReader(){
        try{
            br.close();
        }
        catch(IOException e4){
            System.out.println(e4);
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
