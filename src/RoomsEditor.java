
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
public class RoomsEditor {
    
    String fileName;
    File file;
    Scanner scan = new Scanner(System.in);
    String origin;
    Reader read = new Reader("DefaultRooms.txt");
    FileWriter fw;
    BufferedWriter bw;
    
    public RoomsEditor(String fileName){
        this.fileName = fileName;
        read.openReader();
        origin = read.readAll();
        read.closeReader();
        file = new File(fileName);
    }
    
    public void openEditor(){
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
    
    public void addRoom(String name, String description){
        
    }
    
    public void closeEditor(){
        try{
            bw.close();
        }
        catch(IOException e3){
            System.out.println(e3.toString());
        }
    }
    
}
