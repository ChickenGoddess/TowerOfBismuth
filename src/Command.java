/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class Command {
    
    String direction;
    
    public Command(String direction){
        this.direction = direction;
    }
    
    public String execute(){
        return direction;
    }
    
}
