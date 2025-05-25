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
public class UpdatePassenger implements Update<Passenger>{

    private static  AirportFrame view;
    private static UpdatePassenger instance;

    private UpdatePassenger(AirportFrame airportFrame) {
        view = airportFrame;
    }

    public static UpdatePassenger getUpdatePassenger(AirportFrame airportFrame) {
        if (instance == null) {
            instance = new UpdatePassenger(airportFrame);
        }
        return instance;
    }

    public static UpdatePassenger getUpdatePassenger() {
        if (instance == null) {
            return null;
        }
        return instance;
    }
@Override
    public void newObject(Passenger passenger) {
        view.updatePassengerTable(passenger.clone());
        view.addCbSelectUser(String.valueOf(passenger.getId()));
    }
}
