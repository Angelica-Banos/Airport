/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers.tables;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.storage.StoragePassengers;

/**
 *
 * @author Angie
 */
public class PassengerTable {
    public static  Response getList() {
        try {
            return new Response("Table refreshed", Status.OK, StoragePassengers.getInstance().getList());
        } catch (Exception e) {
            return new Response("Internal Server Error", Status.INTERNAL_SERVER_ERROR);
        }

    }
}
