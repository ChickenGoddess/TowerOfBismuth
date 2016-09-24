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
        Reader read = new Reader("Exits.txt");
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
    
    public void readExit(){
        String left = origin;
        String at = "";
        int exclamation = 0;
        for(int i = 0; i < origin.length(); i++){
            at = origin.substring(i, i+1);
            if(at.equals("#!")){
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
            change = left.substring(left.indexOf("#!")-1, left.indexOf("#!"));
            left = left.replaceFirst(change, "");
            System.out.println(left);
            run = Integer.parseInt(change);
            change = left.substring(left.indexOf("#!"), left.indexOf("\n"));
            roomName = change.replaceAll("#!", "").replaceAll("\n", "");
            left = left.replaceFirst(change, "").replaceFirst("\n", "");
            for(int i = 0; i < run; i++){
                change = left.substring(left.indexOf("&&"), left.indexOf("\n"));
                exitDirection = change.replaceAll("&&", "").replaceAll("\n", "");
                left = left.replaceFirst(change, "").replaceFirst("\n", "");
                change = left.substring(left.indexOf("#"), left.indexOf("\n"));
                exitDesc = change.replaceAll("#", "").replaceAll("\n", "");
                left = left.replaceAll(change, "").replaceFirst("\n", "");
                change = left.substring(left.indexOf("~"), left.indexOf("\n"));
                exitRoom = change.replaceAll("~", "").replaceAll("\n", "");
                left = left.replaceFirst(change, "").replaceFirst("\n", "");
                Exit exit = new Exit(exitDirection, GameState.this.getDungeon().getRoom(roomName), GameState.this.getDungeon().getRoom(exitRoom));
                exit.setDescription(exitDesc);
                GameState.this.getDungeon().getRoom(roomName).addExit(exit);
            }
            left = left.replaceFirst("\n", "");
        }
    }
    
}
