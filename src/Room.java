
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
        Reader reader = new Reader("trinklev3.sav");
        gamestate.readSave("trinklev3.sav");
        String data = gamestate.getInfo();
        int roomHeaderPos = data.indexOf("Room states:") + 13;
        String left = data.substring(0, roomHeaderPos);
        String right = data.substring(roomHeaderPos, data.length());
        right = right.substring(right.indexOf("==="), right.length());
        
        String middle = "";
        for (int i = 0; i < gamestate.getDungeon().checkRooms.size(); i++){
            Room room = gamestate.getDungeon().checkRooms.get(i);
            middle += room.getName() + '\n';
            middle += "beenHere=" + Boolean.toString(room.visited) + '\n';
            if(room.hasItems()){
                middle += "Contents: ";
                int here = items.size();
                int there = 1;
                for(Item item : items){
                    middle += item.getName();
                    if(there < here){
                        middle += ",";
                    }
                }
            }
        }
        String saveData = left + middle + right;
        reader.openWriter();
        reader.writeAll(saveData);
        reader.closeWriter();
    }
    
    public void restoreState(){
        gamestate.readSave("trinklev3.sav");
        String left = gamestate.getInfo();
        if(left.contains(name)){
            left = left.replaceFirst(left.substring(0, left.indexOf("states:")), "");
            left = left.replaceFirst(left.substring(0, left.indexOf("\n")+1), "");
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
                String thisRoom = this.name;
                String change = left.substring(0, left.indexOf("\n")+1);
                String room = left.substring(0, left.indexOf(":"));
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
                if(room.equals(name)){
                    String check = left.substring(0, left.indexOf("---"));
                    left = left.replaceFirst(check.substring(0, check.indexOf("\n") + 1), "");
                    check = check.replaceFirst(check.substring(0, check.indexOf("\n") + 1), "");
                    for(int p = 0; p < gamestate.getDungeon().allItems.size(); p++){
                        for(int m = 0; m < this.items.size(); m++){
                            if(gamestate.getDungeon().allItems.get(p).equals(this.items.get(m))){
                                this.items.remove(m);
                            }
                        }
                    }
                    if(check.contains("Contents: ")){
                        left = left.replaceFirst("Contents: ", "");
                        String roomItems = left.substring(0, left.indexOf("\n"));
                        int here = 1;
                        for(int j = 0; j < roomItems.length(); j++){
                            String chill = roomItems.substring(j, j+1);
                            if(chill.equals(",")){
                                here++;
                            }
                        }
                        for(int j = 0; j < here; j++){
                            if(j == here - 1){
                                String item = roomItems.substring(0);
                                if(!(this.hasItem(item))){
                                    for(int p = 0; p < gamestate.getDungeon().allItems.size(); p++){
                                        if(gamestate.getDungeon().allItems.get(p).getName().equals(item)){
                                            this.addItem(gamestate.getDungeon().allItems.get(p));
                                        }
                                    }
                                }
                                roomItems = roomItems.replaceFirst(item + "\n", "");
                                left = left.replaceFirst(item + "\n", "");
                            }
                            else{
                                String item = roomItems.substring(0, roomItems.indexOf(","));
                                if(!(this.hasItem(item))){
                                    for(int p = 0; p < gamestate.getDungeon().allItems.size(); p++){
                                        if(gamestate.getDungeon().allItems.get(p).getName().equals(item)){
                                            this.addItem(gamestate.getDungeon().allItems.get(p));
                                        }
                                    }
                                }
                                roomItems = roomItems.replaceFirst(item + ",", "");
                            }
                        }
                    }
                }
                if(!(this.name.equals(room))){
                    left = left.replaceFirst(left.substring(0, left.indexOf("\n") + 1), "");
                }
                
                String hex = left.substring(0, left.indexOf("\n"));
                if(hex.contains("Contents: ")){
                    left = left.replaceFirst(hex + "\n", "");
                }
                left = left.replaceFirst("---\n", "");
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
    
    public String returnItems(){
        String here = "";
        if(items.size() == 1){
            here = "There is ";
        }else if(items.size() == 0){
            return "";
        }
        else{
            here = "There is ";
        }
        for(int i = 0; i < items.size(); i++){
            here += "a " + items.get(i).getName() + ",\n";
        }
        return here;
    }
    
    public boolean hasItems(){
        if(!items.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
    
}
