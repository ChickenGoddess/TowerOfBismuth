
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
public class Exit {
    
    String direction;
    String description;
    Room destination;
    Room source;
    
    public Exit(Scanner s, Dungeon dungeon){
        
    }
    
    public Exit(String direction, Room source, Room destination){
        this.direction = direction;
        this.destination = destination;
        this.source = source;
    }
    
    public String getDescription(){
        return description;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public Room getDestination(){
        return destination;
    }
    
    public Room getSource(){
        return source;
    }
    
    public String getDirection(){
        return direction;
    }
    
}
