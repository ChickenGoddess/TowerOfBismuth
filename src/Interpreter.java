
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
public class Interpreter {
    
    public static void main(String[] args){
        
        ArrayList<Exit> arlist = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        CommandFactory cf = new CommandFactory();
        Interpreter interpret = new Interpreter();
        GameState gamestate = new GameState();
        if(gamestate.getDungeon() == null){
            interpret.buildSampleDungeon();
        }
        System.out.println("Welcome to " + gamestate.getDungeon().getName());
        System.out.println("This is a text based adventure. For a commands list "
                + "enter 'c'");
        boolean bool = true;
        while(bool){
            arlist = gamestate.getCurrentRoom().exits;
            System.out.println(gamestate.getCurrentRoom().getName());
            if(gamestate.getCurrentRoom().visited == false){
                System.out.println(gamestate.getCurrentRoom().getDescription());
                for(int i = 0; i < arlist.size(); i++){
                    System.out.println(gamestate.getCurrentRoom().exits.get(i).getDescription());
                }
                gamestate.getCurrentRoom().visited = true;
            }
            System.out.print(">>");
            String input = scan.nextLine().toUpperCase();
            if(input.equals("Q")){
                bool = false;
                break;
            }
            if(gamestate.getCurrentRoom().getExits().contains(cf.parse(input).execute())){
                for(int i = 0; i < arlist.size(); i++){
                    if(gamestate.getCurrentRoom().exits.get(i).getDirection().equals(cf.parse(input).execute())){
                        gamestate.setCurrentRoom(gamestate.getCurrentRoom().exits.get(i).getDestination());
                    }
                }
            }
            else if(cf.parse(input).execute().equals("C")){
                System.out.println("W = go West\nE = go East\nN = go North\n"
                        + "S = go South\nC = Commands\nU = go up\nD = go down\n"
                        + "R = Room Description");
            }
            else if(cf.parse(input).execute().equals("R")){
                gamestate.getCurrentRoom().visited = false;
            }
            else{
                System.out.println("That action is impossible.");
            }
        }
    }
    
    public void buildSampleDungeon(){

        
        GameState gamestate = new GameState();
        Room entry = new Room("Entry");
        Room cave = new Room("Cave");
        Room atrium = new Room("Atrium");
        Room mirror = new Room("Mirror Room");
        Room lava = new Room("Lava Pit");
        Dungeon towerOfBismuth = new Dungeon(entry, "Tower of Bismuth");
        entry.setDescription("You are in a cave. It's dark, but it's possible to see."
                + " Some stalactites hang precariously from the ceiling. Candles are "
                + "lined up all along the walls, dim but visibly helpful.");
        cave.setDescription("You are deeper in the cave. There's little light, but you"
                + " can make out enough to see where you're going. There are some "
                + "candles along the walls, and the room stretches out into a large "
                + "chamber. There are rocks scattered around the ground and you can't "
                + "see the ceiling.");
        Exit entryEast = new Exit("E", entry, cave);
        Exit caveWest = new Exit("W", cave, entry);
        entryEast.setDescription("The cave tunnels to the east and into darkness.");
        caveWest.setDescription("There is a tunnel to the west, partially lit with candles.");
        entry.addExit(entryEast);
        cave.addExit(caveWest);        
        gamestate.initialize(towerOfBismuth);
        gamestate.setCurrentRoom(entry);
    }
    
}
