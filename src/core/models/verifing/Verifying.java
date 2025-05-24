/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.verifing;

/**
 *
 * @author Angie
 */
public class Verifying {

    public Verifying() {
    }
    
    public static  int verifyPlaneId(String id){
        String letters = id.substring(0, 2);
        String numbers = id.substring(id.length()-5);
        
        if(!(Character.isUpperCase(letters.charAt(0)) && Character.isUpperCase(letters.charAt(1)))){
            return 2; //Both letters aren't in uppercase
        }
        
        try {
            int number = Integer.parseInt(numbers);
        } catch (NumberFormatException e) {
            return 1; //Doesn't end in 5 numbers
        }
        
        return 0; //Nice
    }
    
    public static boolean checkAirportId(String id){
        return Character.isUpperCase(id.charAt(0)) && Character.isUpperCase(id.charAt(1)) && Character.isUpperCase(id.charAt(2));
        //True if all characters are upperCase
    }
}
