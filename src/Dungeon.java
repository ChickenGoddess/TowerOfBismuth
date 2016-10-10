
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
    
    GameState gamestate = new GameState();
    String name;
    Hashtable<String, Room> rooms = new Hashtable<>();
    String origin;
    
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
        Reader read = new Reader("Rooms.txt");
        read.openReader();
        origin = read.readAll();
        read.closeReader();
    }
    
    public String getName(){
        return name;
    }
    
    private void addRoom(Room room){
        rooms.put(room.getName(), room);
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
            String change = left.substring(0, left.indexOf("\n"));
            String roomName = change.replaceFirst("\n", "");
            left = left.replaceFirst(change, "").replaceFirst("\n", "");
            change = left.substring(0, left.indexOf("---"));
            String roomDesc = change;
            left = left.replaceFirst(change, "").replaceFirst("---", "");
            left = left.replaceFirst("\n", "");
            Room room = new Room(roomName);
            room.setDescription(roomDesc);
            Dungeon.this.addRoom(room);
        }
    }
    
    public void readExit(){
        String left = origin;
        String x = left.substring(0, left.indexOf("===") + 3);
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
    
    public void storeState(){
        
    }
    
    public void restoreState(){
        
    }
    
}
