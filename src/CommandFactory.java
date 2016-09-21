/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public final class CommandFactory {
    
    public CommandFactory(){
        
    }
    
    public Command parse(String commandString){
        Command command = new Command(commandString);
        return command;
    }
    
}
