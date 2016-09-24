
import java.util.Hashtable;

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
    
    public Dungeon(String name){
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
        String at = "";
        int exclamation = 0;
        for(int i = 0; i < origin.length(); i++){
            at = origin.substring(i, i+1);
            if(at.equals("!")){
                exclamation += 1;
            }
        }
        for(int j = 0; j < exclamation; j++){
            String change = left.substring(left.indexOf("!"), left.indexOf("\n"));
            String roomName = change.replaceAll("!", "").replaceAll("\n", "");
            left = left.replaceAll(change, "").replaceFirst("\n", "");
            change = left.substring(left.indexOf("#"), left.indexOf("\n"));
            String roomDesc = change.replaceAll("#", "");
            left = left.replaceAll(change, "").replaceFirst("\n", "");
            left = left.replaceFirst("\n", "");
            Room room = new Room(roomName);
            room.setDescription(roomDesc);
            Dungeon.this.addRoom(room);
        }
    }
    
}
