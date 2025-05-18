/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;

/**
 *
 * @author Angie
 */
public class PlaneController {

    public PlaneController() {
    }

    public Response createPlane(String id, String brand, String model, String maxCapacity, String Airline) {
        try {

            //Verify lenght of id
            if (id.length() != 7) {
                return new Response("The length of the Id must be 7 characters", Status.BAD_REQUEST);
            }

            //Verify characters in the id
        } catch (Exception e) {
            return new Response("Internal Server Error", Status.INTERNAL_SERVER_ERROR);
        }
        
        
        return new Response("Internal Server Error", Status.INTERNAL_SERVER_ERROR);

    }
}
