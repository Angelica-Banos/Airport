/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.update;

import core.models.Location;
import core.views.AirportFrame;

/**
 *
 * @author Angie
 */
public class UpdateLocations implements Update<Location> {
        private final AirportFrame view;
    private static UpdateLocations instance;

    private UpdateLocations(AirportFrame airportFrame) {
        this.view = airportFrame;
    }

    public static UpdateLocations getUpdateLocations(AirportFrame airportFrame) {
        if (instance == null) {
            instance = new UpdateLocations(airportFrame);
        }
        return instance;
    }

    public static UpdateLocations getUpdateLocations() {
        if (instance == null) {
            return null;
        }
        return instance;
    }
@Override
    public void newObject(Location location) {
        view.addCbDelayLocationId(String.valueOf(location.getAirportId()));
    }

}
