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
        gamestate.getDungeon().readRoom();
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
                        break;
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
            else if(cf.parse(input).execute().equals("ADMIN")){
                boolean bool2 = true;
                while(bool2){
                    System.out.println("Admin box. Press 'q' to exit, 'r' to read rooms, and 'e' to edit rooms.");
                    String str = scan.nextLine().toUpperCase();
                    if(cf.parse(str).execute().equals("R")){
                        Reader read = new Reader("DefaultRooms.txt");
                        read.openReader();
                        System.out.println(read.readAll());
                        read.closeReader();
                    }
                    else if(cf.parse(str).execute().equals("Q")){
                        break;
                    }
                }
            }
            else{
                System.out.println("That action is impossible.");
            }
        }
    }
    
    public void buildSampleDungeon(){

        
        GameState gamestate = new GameState();
        Dungeon towerOfBismuth = new Dungeon("Tower of Bismuth");
        gamestate.initialize(towerOfBismuth);
        gamestate.getDungeon().readRoom();
        gamestate.getDungeon().rooms.get("Entry").readExit();
        gamestate.setCurrentRoom(gamestate.getDungeon().getRoom("Entry"));
    }
    
}
