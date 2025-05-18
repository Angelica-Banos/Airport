package core.models.storage;

import core.models.Flight;
import java.util.ArrayList;
import java.util.List;

public class StorageFlights {

    private static StorageFlights instance;
    private List<Flight> flights;

    private StorageFlights() {
        flights = new ArrayList<>();
    }

    public static StorageFlights getInstance() {
        if (instance == null) {
            instance = new StorageFlights();
        }
        return instance;
    }

    public boolean add(Flight flight) {
        if (!exists(flight.getId())) {
            flights.add(flight);
            return true;
        }
        return false;
    }

    public boolean exists(String id) {
        for (Flight flight : flights) {
            if (flight.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public List<Flight> getAll() {
        return flights;
    }
}
