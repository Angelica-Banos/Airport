/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.controllers.update.UpdatePlanes;
import core.models.Plane;
import core.models.observers.PlaneObserver;
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
public class StoragePlanes implements Storage<Plane> {

    private static StoragePlanes instance;
    private Map<String, Plane> planes;

    public StoragePlanes() {
        this.planes = new HashMap<>();
    }

    public Map<String, Plane> getAll() {
        return this.planes;
    }

    public List<Plane> getAllAsList() {
        return new ArrayList<>(this.planes.values());
    }

    public static StoragePlanes getInstance() {
        if (instance == null) {
            instance = new StoragePlanes();
        }
        return instance;
    }

    @Override
    public boolean add(Plane plane) {
        Plane p = (Plane) plane;
        if (!planes.containsKey(p.getId())) {
            planes.put(p.getId(), p);
            this.notifyObserver(plane);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        try {
            return planes.remove(id) != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Plane plane) {
        if (plane instanceof Plane) {
            Plane p = (Plane) plane;
            if (planes.containsKey(p.getId())) {
                planes.put(p.getId(), p);
                return true;
            }
        }
        return false;
    }

    @Override
    public Plane get(String id) {
        try {
            return planes.get(id);
        } catch (Exception e) {
            return null;
        }
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

                    String id = obj.getString("id");
                    String brand = obj.getString("brand");
                    String model = obj.getString("model");
                    int maxCapacity = obj.getInt("maxCapacity");
                    String airline = obj.getString("airline");

                    Plane plane = new Plane(
                            id, brand, model, maxCapacity, airline
                    );

                    planes.put(id, plane);
                    this.notifyObserver(plane);
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
    public void notifyObserver(Plane plane) {
        new PlaneObserver(UpdatePlanes.getUpdatePlanes()).update(plane);
    }
    @Override
    public List<Plane> getList() {
        List<Plane> listPlanes = new ArrayList<>();
        for (Plane pl : planes.values()) {
            listPlanes.add(pl.clone());
        }
        return listPlanes;
    }

}
