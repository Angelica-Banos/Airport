/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Location;
import core.models.Passenger;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Angie
 */
public class StorageLocations implements Storage<Location> {

    private static StorageLocations instance;
    private Map<String, Location> locations;

    private StorageLocations() {
        locations = new HashMap<>();
    }

    public static StorageLocations getInstance() {
        if (instance == null) {
            instance = new StorageLocations();
        }
        return instance;
    }

    @Override
    public boolean add(Object obj) {
        if (obj instanceof Location) {
            Location lc = (Location) obj;
            if (!locations.containsKey(lc.getAirportId())) {
                locations.put(lc.getAirportId(), lc);
                return true;
            }
        }
        return false;
    }


    @Override
    public Location get(String id) {
        try {
            return locations.get(id);
        } catch (NotFoundException e) {
            return null;
        }
    }

   
    @Override
    public boolean delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean loadFromJson(String filepath) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

    }

    @Override
    public boolean add(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
