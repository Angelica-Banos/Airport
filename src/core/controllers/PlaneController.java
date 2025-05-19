/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Plane;
import core.models.storage.StoragePlanes;
import core.models.verifing.Verifying;

/**
 *
 * @author Angie
 */
public class PlaneController {

    public PlaneController() {
    }

    public static Response createPlane(String id, String brand, String model, String maxCapacity, String airline) {
        try {
            if (id.trim().equals("")) {
                return new Response("The Id can't be empty", Status.BAD_REQUEST);
            }

            //Verify length of id
            if (id.length() != 7) {
                return new Response("The length of the Id must be 7 characters", Status.BAD_REQUEST);
            }

            //Verify characters in the id
            switch (Verifying.verifyPlaneId(id)) {
                case 1:
                    return new Response("The last 5 characters of the Id must be numbers", Status.BAD_REQUEST);
                case 2:
                    return new Response("The first 2 characters must be capital letters", Status.BAD_REQUEST);
            }

            //Verify that the rest of data aren't empty
            if (brand.trim().equals("")) {
                return new Response("The brand can't be empty", Status.BAD_REQUEST);
            }
            if (model.trim().equals("")) {
                return new Response("The brand can't be empty", Status.BAD_REQUEST);
            }

            if (maxCapacity.trim().equals("")) {
                return new Response("The capacity can't be empty", Status.BAD_REQUEST);
            }

            if (airline.trim().equals("")) {
                return new Response("The airline can't be empty", Status.BAD_REQUEST);
            }
            //Verify that MaxCapacity is a positive integer
            int intMaxCapacity;
            try {
                intMaxCapacity = Integer.parseInt(maxCapacity);
            } catch (NumberFormatException ex) {
                return new Response("The capacity must be a number", Status.BAD_REQUEST);
            }

            if (intMaxCapacity < 0) {
                return new Response("The capacity must be positve", Status.BAD_REQUEST);
            }

            //Add to storage/Check if the if is taken
            if (!addPlane(new Plane(id, brand, model, intMaxCapacity, airline))) {
                return new Response("There's already a passenger with that Id", Status.BAD_REQUEST);
            }

            //All good
            return new Response("Plane created succesfully", Status.CREATED);
        } catch (Exception e) {
            return new Response("Internal Server Error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static boolean addPlane(Plane plane) {
        StoragePlanes planeStorage = StoragePlanes.getInstance();
        return planeStorage.add(plane);
    }
}
