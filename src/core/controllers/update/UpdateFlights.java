/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.update;

import core.models.Flight;
import core.views.AirportFrame;

/**
 *
 * @author Angie
 */
public class UpdateFlights implements Update<Flight>{
    
    private final AirportFrame view;
    private static UpdateFlights instance;

    private UpdateFlights(AirportFrame airportFrame) {
        this.view = airportFrame;
    }

    public static UpdateFlights getUpdateFlights(AirportFrame airportFrame) {
        if (instance == null) {
            instance = new UpdateFlights(airportFrame);
        }
        return instance;
    }

    public static UpdateFlights getUpdateFlights() {
        if (instance == null) {
            return null;
        }
        return instance;
    }
@Override
    public void newObject(Flight flight) {
        view.addCbDelayFlightId(String.valueOf(flight.getId()));
    }

}
