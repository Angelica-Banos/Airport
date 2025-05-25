/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Location;
import core.models.observers.LocationObserver;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public List<Location> getAllAsList() {
        return new ArrayList<>(this.locations.values());
    }

    public static StorageLocations getInstance() {
        if (instance == null) {
            instance = new StorageLocations();
        }
        return instance;
    }

    @Override
    public boolean add(Location location) {
        if (location instanceof Location) {
            Location lc = (Location) location;
            if (!locations.containsKey(lc.getAirportId())) {
                locations.put(lc.getAirportId(), lc);
                this.notifyObserver(location);
                return true;
            }
        }
        return false;
    }

    @Override
    public Location get(String id) {
        try {
            return locations.get(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            return locations.remove(id) != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Location location) {
        if (locations.containsKey(location.getAirportId())) {
            locations.put(location.getAirportId(), location);
            return true;
        }
        return false;
    }

    public Map<String, Location> getAll() {
        return this.locations;
    }

    @Override
    public boolean loadFromJson(String resourcePath) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            try ( InputStream inputStream = classLoader.getResourceAsStream(resourcePath)) {
                if (inputStream == null) {
                    System.err.println("Recurso no encontrado: " + resourcePath);
                    return false;
                }

                String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                JSONArray jsonArray = new JSONArray(content);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject location = jsonArray.getJSONObject(i);

                    String airportId = location.getString("airportId");
                    String airportName = location.getString("airportName");
                    String airportCity = location.getString("airportCity");
                    String airportCountry = location.getString("airportCountry");
                    long airportLatitude = location.getLong("airportLatitude");
                    long airportLongitude = location.getLong("airportLatitude");

                    Location obj = new Location(airportId, airportName, airportCity, airportCountry, airportLatitude, airportLongitude);

                    locations.put(airportId, obj);
                    this.notifyObserver(obj);
                }

                return true;
            }
        } catch (IOException e) {
            System.err.println("Error leyendo el recurso: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error procesando JSON: " + e.getMessage());
        }

        return false;
    }

    @Override
    public void notifyObserver(Location location) {
        new LocationObserver().update(location);
    }

    @Override
    public List<Location> getList() {
                        List<Location> listLocations = new ArrayList<>();
        for (Location lc : locations.values()) {
            listLocations.add(lc.clone());
        }
        return listLocations;
    }

}
