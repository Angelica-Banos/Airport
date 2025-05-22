/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.update;
import core.models.Passenger;
import core.views.AirportFrame;


/**
 *
 * @author Angie
 */
public class UpdateUsers {
    private final AirportFrame view;
    private static UpdateUsers instance;
    
    private UpdateUsers(AirportFrame airportFrame) {
        this.view = airportFrame;
    }
    
    public static UpdateUsers getUpdateUsers(AirportFrame airportFrame){
        if (instance == null) {
            instance = new UpdateUsers(airportFrame);
        } 
        return instance;
    }
    public static UpdateUsers getUpdateUsers(){
        if (instance == null) {
           return null;
        } 
        return instance;
    }
    
    
    public void newPassengerMade(Passenger passenger){
        view.addCbSelectUser(String.valueOf(passenger.getId()));
    }
}
