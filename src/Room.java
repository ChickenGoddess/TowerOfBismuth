
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
    
    GameState gamestate = new GameState();
    String description;
    String name;
    boolean visited;
    ArrayList<Exit> exits = new ArrayList<>();
    String origin;
    
    public Room(String name){
        this.name = name;
        Reader read = new Reader("Exits.txt");
        read.openReader();
        origin = read.readAll();
        read.closeReader();
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
    
    public void readExit(){
        String left = origin;
        String at = "";
        int exclamation = 5;
        for(int i = 0; i < origin.length(); i++){
            at = origin.substring(i, i+2);
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
            System.out.println(left);
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
                Exit exit = new Exit(exitDirection, gamestate.getDungeon().getRoom(roomName), gamestate.getDungeon().getRoom(exitRoom));
                exit.setDescription(exitDesc);
                gamestate.getDungeon().getRoom(roomName).addExit(exit);
            }
            left = left.replaceFirst("\n", "");
        }
    }
    
}
