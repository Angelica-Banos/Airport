/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.observers;

import core.controllers.update.Update;
import core.controllers.update.UpdateLocations;
import core.models.Location;

/**
 *
 * @author Angie
 */
public class LocationObserver implements Observer<Location> {
   Update  locHandler;
   
    public LocationObserver(Update locHandler) {
        this.locHandler = locHandler;
    }
    @Override
    public void update(Location location) {
        locHandler.newObject(location);
    }
    
}
