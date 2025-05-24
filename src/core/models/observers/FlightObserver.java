/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.observers;

import core.controllers.update.UpdateFlights;
import core.models.Flight;

/**
 *
 * @author Angie
 */
public class FlightObserver implements Observer<Flight> {
     @Override
    public void update(Flight flight) {
        UpdateFlights.getUpdateFlights().newFlightMade(flight);
    }
}
