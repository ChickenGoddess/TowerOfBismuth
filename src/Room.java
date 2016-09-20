
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class Room {
    
    String description;
    String name;
    boolean visited;
    ArrayList<Exit> exits = new ArrayList<>();
    
    public Room(String name){
        this.name = name;
    }
    
    public String getDescription(){
        return description;
    }
    
    public String getName(){
        return name;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public void addExit(Exit exit){
        exits.add(exit);
    }
    
    public String getExits(){
        String s = "";
        for (Exit exit : exits) {
            s = s + exit.getDirection() + "\n";
        }
        return s;
    }
    
    public Room leaveBy(String direction){
        for (Exit exit : exits) {
            if(exit.getDirection().equals(direction)){
                return exit.getDestination();
            }
        }
        return null; 
    }
    public int getExitsSize(){
        return exits.size();
    }
    
}
