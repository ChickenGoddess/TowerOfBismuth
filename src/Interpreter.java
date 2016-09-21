
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
        atrium.setDescription("The room is full of various plants and floral arrangements. "
                + "There's a large dome of glass with light filtering through from what "
                + "looks like outside, but that's impossible from your depth.");
        mirror.setDescription("This room has mirrors on all sides. The mirrors seem to go on for infinity.");
        lava.setDescription("The room is boiling hot and your skin feels like "
                + "it's being set on fire just standing here. There is a giant pool "
                + "of lava in the middle of the floor, barring any passage whatsoever.");
        Exit entryEast = new Exit("E", entry, cave);
        Exit caveWest = new Exit("W", cave, entry);
        Exit caveSouth = new Exit("S", cave, lava);
        Exit caveNorth = new Exit("N", cave, mirror);
        Exit caveEast = new Exit("E", cave, atrium);
        Exit mirrorSouth = new Exit("S", mirror, cave);
        Exit atriumWest = new Exit("W", atrium, cave);
        Exit lavaNorth = new Exit("N", lava, cave);
        entryEast.setDescription("The cave tunnels to the east and into darkness.");
        caveWest.setDescription("There is a tunnel to the west, partially lit with candles.");
        caveSouth.setDescription("There is a tunnel to the south. A vicous heat is coming from it.");
        caveNorth.setDescription("A small crawlhole is to the North of you. It narrows to a point, but is passable.");
        caveEast.setDescription("There is natural light coming from a path to the East. It smells nice.");
        mirrorSouth.setDescription("The path to the south narrows to a small crawlhole into darkness.");
        atriumWest.setDescription("There is a defined path leading to the west into darkness.");
        lavaNorth.setDescription("The path to the north is significantly cooler than right here!");
        entry.addExit(entryEast);
        cave.addExit(caveEast);
        cave.addExit(caveWest);
        cave.addExit(caveSouth);
        cave.addExit(caveNorth);
        lava.addExit(lavaNorth);
        mirror.addExit(mirrorSouth);
        atrium.addExit(atriumWest);
        gamestate.initialize(towerOfBismuth);
        gamestate.setCurrentRoom(entry);
    }
    
}
