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
    public boolean update(Object obj) {
        if (obj instanceof Location) {
            Location lc = (Location) obj;
            if (locations.containsKey(lc.getAirportId())) {
                locations.put(lc.getAirportId(), lc);
                return true;
            }
        }
        return false;
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
                    JSONObject obj = jsonArray.getJSONObject(i);

                    String airportId = obj.getString("airportId");
                    String airportName = obj.getString("airportName");
                    String airportCity = obj.getString("airportCity");
                    String airportCountry = obj.getString("airportCountry");
                    long airportLatitude = obj.getLong("airportLatitude");
                    long airportLongitude = obj.getLong("airportLatitude");

                    Location location = new Location(
                            airportId, airportName, airportCity, airportCountry, airportLatitude, airportLongitude
                    );

                    locations.put(airportId, location);
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

}
