/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.update;

import core.models.Plane;
import core.views.AirportFrame;

/**
 *
 * @author Angie
 */
public class UpdatePlanes implements Update<Plane>{

    private final AirportFrame view;
    private static UpdatePlanes instance;

    private UpdatePlanes(AirportFrame airportFrame) {
        this.view = airportFrame;
    }

    public static UpdatePlanes getUpdatePlanes(AirportFrame airportFrame) {
        if (instance == null) {
            instance = new UpdatePlanes(airportFrame);
        }
        return instance;
    }

    public static UpdatePlanes getUpdatePlanes() {
        if (instance == null) {
            return null;
        }
        return instance;
    }
@Override
    public void newObject(Plane plane) {
        view.addCbFlightPlane(String.valueOf(plane.getId()));
    }

}
