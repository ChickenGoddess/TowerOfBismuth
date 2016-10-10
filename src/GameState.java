/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public final class GameState {
    
    String origin;
    private static Room currentRoom;
    private static Dungeon dungeon;
    
    public GameState(){
        
    }
    
    public void readSave(String filename){
        Reader read = new Reader(filename);
        read.openReader();
        origin = read.readAll();
        read.closeReader();
    }
    
    public void initialize(Dungeon dungeon){
        GameState.dungeon = dungeon;
    }
    
    public Dungeon getDungeon(){
        return dungeon;
    }
    
    public Room getCurrentRoom(){
        return currentRoom;
    }
    
    public void setCurrentRoom(Room room){
        currentRoom = room;
    }
    
    
    
}
