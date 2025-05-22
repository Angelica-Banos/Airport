package core.models.storage;


import core.models.Flight;
import core.models.Location;
import core.models.Plane;
import core.models.persistency.ReadJSonFlight;
import java.util.ArrayList;
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
  @Override

      public boolean loadFromJson(String path) {return true;}
//        List<Plane> planes = new ArrayList<>(StoragePlanes.getInstance().getAll().values());
//        List<Location> locations = new ArrayList<>(StorageLocations.getInstance().getAll().values());
//
//        ReadJSonFlight reader = new ReadJSonFlight(planes, locations);
//        List<Flight> flightsList = reader.readFromFile(path);
//
//        if (flightsList == null || flightsList.isEmpty()) {
//            return false;
//        }
//
//        for (Flight flight : flightsList) {
//            this.flights.put(flight.getId(), flight);
//        }
//
//        return true;
//        
//
//        return !flights.isEmpty();
//    }

    
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
    public Flight get(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean add(Flight flight) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(Flight flight) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}


