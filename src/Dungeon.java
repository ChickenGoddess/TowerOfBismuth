
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
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
public class Dungeon {
    
    private GameState gamestate = new GameState();
    private String name;
    private HashMap<String, Room> rooms = new HashMap<>();
    public ArrayList<Room> checkRooms = new ArrayList<>();
    private String origin;
    ArrayList<Item> allItems = new ArrayList<>();
    
    public Dungeon(String filename){
        Reader read = new Reader(filename);
        read.openReader();
        origin = read.readAll();
        read.closeReader();
        String left = origin;
        name = left.substring(0, left.indexOf("\n"));
    }
    
    public Dungeon(Room entry, String name){
        this.name = name;
        Reader read = new Reader("trinkle3.bork");
        read.openReader();
        origin = read.readAll();
        read.closeReader();
    }
    
    public String getName(){
        return name;
    }
    
    private void addRoom(Room room){
        rooms.put(room.getName(), room);
        checkRooms.add(room);
    }
    
    public Room getRoom(String roomName){
        Room room = null;
        for(int i = 0; i < rooms.size(); i++){
            if(rooms.containsKey(roomName)){
                room = rooms.get(roomName);
            }
        }
        return room;
    }
    
    public void readRoom(){
        String left = origin;
        String x = left.substring(0, left.indexOf("===") + 4);
        left = left.replaceFirst(x, "");
        x = left.substring(0, left.indexOf("===") + 4);
        left = left.replaceFirst(x, "");
        x = left.substring(left.indexOf("==="), left.length());
        left = left.replaceFirst(x, "");
        left = left.replaceFirst("Rooms:\n", "");
        String at = "";
        int exclamation = 0;
        for(int i = 0; i < left.length()-3; i++){
            at = left.substring(i, i+3);
            if(at.equals("---")){
                exclamation += 1;
            }
        }
        for(int j = 0; j < exclamation; j++){
            int here = 0;
            String change = left.substring(0, left.indexOf("\n"));
            String roomName = change.replaceFirst("\n", "");
            left = left.replaceFirst(change, "").replaceFirst("\n", "");
            String check = left.substring(0, left.indexOf("\n"));
            if(check.contains("Contents:")){
                left = left.replaceFirst("Contents: ", "");
                check = left.substring(0, left.indexOf("\n"));
                here = 1;
                String comma = "";
                for(int i = 0; i < check.length(); i++){
                    comma = left.substring(i, i+1);
                    if(comma.equals(",")){
                        here++;
                    }
                }
                left = left.replaceFirst(check, "");
                left = left.replaceFirst("\n", "");
            }
            change = left.substring(0, left.indexOf("---"));
            String roomDesc = change;
            left = left.replaceFirst(change, "").replaceFirst("---", "");
            left = left.replaceFirst("\n", "");
            Room room = new Room(roomName);
            room.setDescription(roomDesc);
            Dungeon.this.addRoom(room);
            if(here > 0){    
                for(int i = 0; i < here; i++){
                    String itemName = "";
                    if(check.contains(",")){
                        itemName = check.substring(0, check.indexOf(","));
                        for(int p = 0; p < gamestate.getDungeon().allItems.size(); p++){
                            if(gamestate.getDungeon().allItems.get(p).getName().equals(itemName)){
                                room.addItem(this.allItems.get(p));
                            }
                        }
                        check = check.replaceFirst(itemName + ",", "");
                    }
                    else{
                        itemName = check.substring(0, check.length());
                        for(int p = 0; p < gamestate.getDungeon().allItems.size(); p++){
                            if(gamestate.getDungeon().allItems.get(p).getName().equals(itemName)){
                                room.addItem(this.allItems.get(p));
                            }
                        }
                        check = check.replaceFirst(itemName, "");
                    }
                }
            }
        }
    }
    
    public void readExit(){
        String left = origin;
        String x = left.substring(0, left.indexOf("===") + 3);
        left = left.replace(x, "");
        x = left.substring(0, left.indexOf("===") + 3);
        left = left.replace(x, "");
        x = left.substring(0, left.indexOf("===") + 3);
        left = left.replace(x, "");
        left = left.replace("\nExits:\n", "");
        String at = "";
        int exclamation = 0;
        for(int i = 0; i < left.length()-3; i++){
            at = left.substring(i, i+3);
            if(at.equals("---")){
                exclamation += 1;
            }
        }
        for(int j = 0; j < exclamation; j++){
            String roomName = "";
            String exitDesc = "";
            String exitDirection = "";
            String exitRoom = "";
            String change = "";
            int run;
            change = left.substring(0, left.indexOf("\n"));
            left = left.replaceFirst(change, "").replaceFirst("\n", "");
            roomName = change;
            change = left.substring(0, left.indexOf("\n"));
            exitDirection = change;
            left = left.replaceFirst(change, "").replaceFirst("\n", "");
            change = "There is a path " + change;
            exitDesc = change;
            change = left.substring(0, left.indexOf("\n"));
            exitRoom = change;
            left = left.replaceFirst(change, "").replaceFirst("\n---\n", "");
            Exit exit = new Exit(exitDirection, gamestate.getDungeon().getRoom(roomName), gamestate.getDungeon().getRoom(exitRoom));
            exit.setDescription(exitDesc);
            gamestate.getDungeon().getRoom(roomName).addExit(exit);
        }
    }
    
    public void readItems(){
        String left = origin;
        String change = left.substring(0, left.indexOf("===") + 4);
        left = left.replaceFirst(change, "");
        left = left.substring(7, left.indexOf("==="));
        String at = "";
        int line = 0;
        for(int i = 0; i < left.length() - 3; i++){
            at = left.substring(i, i+3);
            if(at.equals("---")){
                line++;
            }
        }
        
        for(int i = 0; i < line; i++){
            String name = left.substring(0, left.indexOf("\n"));
            left = left.replaceFirst(name + "\n", "");
            String weight = left.substring(0, left.indexOf("\n"));
            int we = Integer.parseInt(weight);
            Item item = new Item(name, we);
            left = left.replaceFirst(weight + "\n", "");
            Boolean bool = true;
            while(bool){
                String check = left.substring(0, left.indexOf("---") + 3);
                if(check.equals("---")){
                    left = left.replaceFirst("---\n", "");
                    this.addItem(item);
                    break;
                }
                else{
                    String verb = left.substring(0, left.indexOf(":"));
                    left = left.replaceFirst(verb + ":", "");
                    String message = left.substring(0, left.indexOf("\n"));
                    left = left.replaceFirst(message + "\n", "");
                    item.addMessage(verb, message);
                }
            }
        }
    }
    
    public void storeState(){
        Reader reader = new Reader("trinklev3.sav");
        gamestate.readSave("trinklev3.sav");
        String left = gamestate.getInfo();
        left = left.substring(0, left.indexOf("Current room: "));
        String rhere = gamestate.getCurrentRoom().getName();
        left = left + "Adventurer:\nCurrent room: " + rhere + "\nInventory: ";
        reader.openWriter();
        reader.writeAll(left);
        reader.closeWriter();
    }
    
    public void restoreState(){
        gamestate.readSave("trinklev3.sav");
        String left = gamestate.getInfo();
        left = left.substring(left.indexOf("==="), left.length());
        left = left.replaceFirst("===\n", "");
        left = left.replaceFirst("Adventurer:\n", "");
        left = left.replaceFirst("Current room: ", "");
        left = left.substring(0, left.indexOf("\n"));
        gamestate.setCurrentRoom(this.getRoom(left));
    }
    
    public void addItem(Item item){
        allItems.add(item);
    }
    
    public Item getItem(Item item){
        for(int i = 0; i < allItems.size(); i++){
            if(allItems.get(i).equals(item)){
                return allItems.get(i);
            }
        }
        return null;
    }
    
    public String getItems(){
        
    }
}
