package core.models.storage;

import core.controllers.update.UpdatePassenger;
import core.models.Passenger;
import core.models.observers.PassengerObserver;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoragePassengers implements Storage<Passenger> {

    private static StoragePassengers instance;
    private Map<Long, Passenger> passengers;

    private StoragePassengers() {
        passengers = new HashMap<>();
    }

   
    public boolean exists(String id) {
        try {
            
            return passengers.containsKey(Long.valueOf(id));
        } catch (NumberFormatException e) {
            
            return false;
        }
    }

    public static StoragePassengers getInstance() {
        if (instance == null) {
            instance = new StoragePassengers();
        }
        return instance;
    }

   
    public Map<Long, Passenger> getAllAsMap() {
        return this.passengers;
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

                    long id = obj.getLong("id");
                    String firstname = obj.getString("firstname");
                    String lastname = obj.getString("lastname");
                    LocalDate birthDate = LocalDate.parse(obj.getString("birthDate"));
                    int countryPhoneCode = obj.getInt("countryPhoneCode");
                    long phone = obj.getLong("phone");
                    String country = obj.getString("country");

                    Passenger passenger = new Passenger(
                            id, firstname, lastname, birthDate, countryPhoneCode, phone, country
                    );

                    passengers.put(id, passenger);
                    this.notifyObserver(passengers.get(id));
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
    public Passenger get(String id) {
        try {
            return passengers.get(Long.valueOf(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public boolean add(Passenger passenger) {
        
        if (!passengers.containsKey(passenger.getId())) {
            passengers.put(passenger.getId(), passenger);
            this.notifyObserver(passenger);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        try {
            return passengers.remove(Long.valueOf(id)) != null;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean update(Passenger obj) {
        try {
            passengers.put(obj.getId(), obj);
            return true;

        } catch (Exception e) {
            return false;

        }
    }

    public Collection<Passenger> getAll() {
        return passengers.values();
    }

    @Override
    public void notifyObserver(Passenger passenger) {
        new PassengerObserver(UpdatePassenger.getUpdatePassenger()).update(passenger);
    }

    @Override
    public List<Passenger> getList(){
        List<Passenger> listPassengers = new ArrayList<>();
        for (Passenger ps : passengers.values()) {
            listPassengers.add(ps.clone());
        }
        return listPassengers;
    }
}