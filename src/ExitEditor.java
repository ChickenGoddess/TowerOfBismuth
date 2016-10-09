
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
public class ExitEditor {
    
    String fileName;
    File file;
    Scanner scan = new Scanner(System.in);
    String origin;
    FileWriter fw;
    BufferedWriter bw;
    
    public ExitEditor(String fileName){
        this.fileName = fileName;
        file = new File(fileName);
    }
    
    public void openEditor(){
        Reader read = new Reader("Rooms.txt");
        read.openReader();
        origin = read.readAll();
        read.closeReader();
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
    
   /* public void addExit(String direction, String roomName, String exitRoom){
        if(origin.equals("")){
            origin = origin + "!" + name + "\n#" + description;
        }
        else{
            origin = origin + "\n!" + name + "\n#" + description;
        }
        System.out.println(origin);
        try{
            bw.write(origin, 0, origin.length());
        }
        catch(IOException ee){
            System.out.println(ee);
        }
    }*/
    
    public void closeEditor(){
        try{
            bw.close();
        }
        catch(IOException e3){
            System.out.println(e3.toString());
        }
    }
    
}
