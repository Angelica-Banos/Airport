/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.observers;

import core.controllers.update.Update;
import core.models.Plane;

/**
 *
 * @author Angie
 */
public class PlaneObserver implements Observer<Plane>{
      Update  planeHandler;
   
    public PlaneObserver(Update planeHandler) {
        this.planeHandler = planeHandler;
    }
        @Override
    public void update(Plane plane) {
        planeHandler.newObject(plane);
    }
}
