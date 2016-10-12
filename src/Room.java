
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.BufferedWriter;
import java.io.FileWriter;

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
    
    
    GameState gamestate = new GameState();
    String description;
    String name;
    boolean visited;
    ArrayList<Exit> exits = new ArrayList<>();
    String origin;
    
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
    
    public void storeState(){
        Reader reader = new Reader("trinklev2.sav");
        gamestate.readSave("trinklev2.sav");
        String left = gamestate.getInfo();
        String right = left.substring(0, left.indexOf("Room states:") + 13);
        left = left.replace(right, "");
        String middle = left.substring(0, left.indexOf("==="));
        left = left.replace(middle + "===\n", "");
        for(int i = 0; i < gamestate.getDungeon().checkRooms.size(); i++){
            if(gamestate.getDungeon().checkRooms.get(i).visited == true){
                String still = gamestate.getDungeon().checkRooms.get(i).getName();
                right = right + still + ":\nbeenHere=true\n---\n";
            }
        }
        right = right + "===\n" + left;
        reader.openWriter();
        reader.writeAll(right);
        reader.closeWriter();
    }
    
    public void restoreState(String filename){
        String left = gamestate.getInfo();
        if(left.contains(name)){
            left = left.replace(left.substring(0, left.indexOf("states:")), "");
            left = left.replace(left.substring(0, left.indexOf("\n")+1), "");
            int exc = 0;
            String at = "";
            for(int i = 0; i < left.length()-3; i++){
                at = left.substring(i, i+3);
                if(at.equals("---")){
                    exc += 1;
                }
            }
            left = left.replace(left.substring(left.indexOf("==="), left.length()), "");
            for(int i = 0; i < exc; i++){
                String change = left.substring(0, left.indexOf("\n")+1);
                String room = left.substring(0, left.indexOf("\n")-1);
                if(room.equals(name)){
                    left = left.replace(change, "");
                    String beenhere = left.substring(0, left.indexOf("\n")+1);
                    if(beenhere.contains("true")){
                        visited = true;
                    }
                    else{
                        visited = false;
                    }
                }
            }
        }
    }
    
}
