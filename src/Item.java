
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
public class Item {
    
    Reader read;
    private String name;
    private int weight;
    private Hashtable<String, String> messages = new Hashtable<>();
    
    public Item(String name, int weight){
        this.name = name;
        this.weight = weight;
    }
    
    public boolean goesBy(String name){
        return this.name.equals(name);
    }
    
    public String getName(){
        return name;
    }
    
    public String getMessage(String verb){
        return messages.get(verb);
    }
    
    public int getWeight(){
        return weight;
    }
    
    public void addMessage(String verb, String message){
        messages.put(verb, message);
    }
    
}
