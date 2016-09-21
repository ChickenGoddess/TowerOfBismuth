
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
    
    String name;
    Hashtable<String, Room> rooms = new Hashtable<>();
    
    public Dungeon(Room initial, String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public void addRoom(Room room){
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
    
}