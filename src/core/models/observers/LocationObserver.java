/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.observers;

import core.controllers.update.UpdateLocations;
import core.models.Location;

/**
 *
 * @author Angie
 */
public class LocationObserver implements Observer<Location> {

    @Override
    public void update(Location location) {
        UpdateLocations.getUpdateLocations().newLocationMade(location);
    }
    
}
