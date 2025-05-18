/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.verifing;

/**
 *
 * @author Angie
 */
public class Verifing {

    public Verifing() {
    }
    
    public int verifyPlaneId(String id){
        String letters = id.substring(0, 2);
        String numbers = id.substring(id.length()-5);
        
        
        boolean uppercase = Character.isUpperCase(letters.charAt(0)) && Character.isUpperCase(letters.charAt(1));
        if(!uppercase){
            return 0; //Both letters aren't in uppercase
        }
        
        try {
            int number = Integer.parseInt(numbers);
        } catch (NumberFormatException e) {
            return 1; //Doesn't end in 5 numbers
        }
        
        return 2; //Nice
    }
}
