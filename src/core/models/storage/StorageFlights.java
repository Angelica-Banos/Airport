package core.models.storage;

import core.models.Flight;
import core.models.Location;
import core.models.Passenger; // ¡NUEVO! Importar Passenger
import core.models.Plane;
import core.models.observers.FlightObserver;
import core.models.persistency.ReadJSonFlight;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageFlights implements Storage<Flight> {

    private static StorageFlights instance;
    private Map<String, Flight> flightsMap;

    private StorageFlights() {
        this.flightsMap = new HashMap<>();
    }

    public static StorageFlights getInstance() {
        if (instance == null) {
            instance = new StorageFlights();
        }
        return instance;
    }

    public boolean exists(String id) {
        return flightsMap.containsKey(id);
    }

    @Override
    public boolean loadFromJson(String path) {
        Map<String, Plane> planes = StoragePlanes.getInstance().getAll();
        Map<String, Location> locations = StorageLocations.getInstance().getAll();
        //  Obtener el mapa de pasajeros de StoragePassengers ---
        Map<Long, Passenger> passengers = StoragePassengers.getInstance().getAllAsMap(); // Asegúrate de que StoragePassengers tenga este método

        
        ReadJSonFlight reader = new ReadJSonFlight(planes, locations, passengers); // Pasar el mapa de pasajeros

        List<Flight> flightsList = reader.readFromFile(path);

        if (flightsList == null || flightsList.isEmpty()) {
            return false;
        }

        for (Flight flight : flightsList) {
            this.flightsMap.put(flight.getId(), flight);
            this.notifyObserver(flight);
        }
        return true;
    }

    public Map<String, Flight> getAll() {
        return this.flightsMap;
    }

    public List<Flight> getAllAsList() {
        return new ArrayList<>(this.flightsMap.values());
    }

    public Flight getById(String id) {
        return this.flightsMap.get(id);
    }

    @Override
    public boolean add(Flight flight) {
        if (flight == null || flightsMap.containsKey(flight.getId())) {
            return false;
        }
        flightsMap.put(flight.getId(), flight);
        this.notifyObserver(flight);
        return true;
    }

    @Override
    public Flight get(String id) {
        return flightsMap.get(id);
    }

    @Override
    public boolean delete(String id) {
        if (!flightsMap.containsKey(id)) {
            return false;
        }
        flightsMap.remove(id);
        return true;
    }

    @Override
    public boolean update(Flight flight) {
        if (flight == null || !flightsMap.containsKey(flight.getId())) {
            return false;
        }
        flightsMap.put(flight.getId(), flight);
        
        return true;
    }

    @Override
    public void notifyObserver(Flight flight) {
        new FlightObserver().update(flight);
    }

    @Override
    public List<Flight> getList() {
        List<Flight> sortedFlights = new ArrayList<>();
        for (Flight fl : flightsMap.values()) {
            sortedFlights.add(fl.clone());
        }
        sortedFlights.sort(Comparator.comparing(Flight::getId));
        return sortedFlights;
    }
}