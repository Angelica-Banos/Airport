/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.observers;

import core.controllers.update.UpdateUsers;
import core.models.Passenger;

/**
 *
 * @author Angie
 */
public class PassengerObserver implements Observer<Passenger>{

    @Override
    public void update(Passenger passenger) {
        UpdateUsers.newPassengerMade(passenger);
    }
    
}
