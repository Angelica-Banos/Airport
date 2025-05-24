/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.observers;

import core.controllers.update.UpdatePlanes;
import core.models.Plane;

/**
 *
 * @author Angie
 */
public class PlaneObserver implements Observer<Plane>{
        @Override
    public void update(Plane plane) {
        UpdatePlanes.getUpdatePlanes().newPlaneMade(plane);
    }
}
