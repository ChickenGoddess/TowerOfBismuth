
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
    ArrayList<Item> items = new ArrayList<>();
    
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
        String data = gamestate.getInfo();
        int roomHeaderPos = data.indexOf("Room states:") + 13;
        String left = data.substring(0, roomHeaderPos);
        String right = data.substring(roomHeaderPos, data.length());
        right = right.substring(right.indexOf("==="), right.length());
        
        String middle = "";
        // Note: Assumes that there will always be at least one room
        for (int i = 0; i < gamestate.getDungeon().checkRooms.size(); i++){
            Room room = gamestate.getDungeon().checkRooms.get(i);
            middle += room.getName() + '\n';
            middle += "beenHere=" + Boolean.toString(room.visited) + '\n';
            middle += "---\n";
        }
        String saveData = left + middle + right;
        reader.openWriter();
        reader.writeAll(saveData);
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
                String room = left.substring(0, left.indexOf("\n"));
                left = left.replaceFirst(change, "");
                if(room.equals(name)){
                    String beenhere = left.substring(0, left.indexOf("\n")+1);
                    if(beenhere.contains("true")){
                        this.visited = true;
                    }
                    else{
                        this.visited = false;
                    }
                }
                left = left.replaceFirst(left.substring(0, left.indexOf("\n")) + "\n---\n", "");
            }
        }
    }
    
    public void addItem(Item item){
        items.add(item);
    }
    
    public Item getItem(Item item){
        if(items.contains(item)){
            return item;
        }
        else{
            return null;
        }
    }
    
    public void removeItem(Item item){
        items.remove(item);
    }
    
    public boolean hasItem(String item){
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).getName().equals(item)){
                return true;
            }
        }
        return false;
    }
    
}
