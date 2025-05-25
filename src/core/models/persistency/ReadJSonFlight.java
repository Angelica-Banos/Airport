package core.models.persistency;

import core.models.Flight;
import core.models.Plane;
import core.models.Location;
import core.models.Passenger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReadJSonFlight implements Reader<Flight> {

    private final Map<String, Plane> planesMap;
    private final Map<String, Location> locationsMap;
    private final Map<Long, Passenger> passengersMap;

    public ReadJSonFlight(Map<String, Plane> planesMap, Map<String, Location> locationsMap, Map<Long, Passenger> passengersMap) {
        this.planesMap = planesMap;
        this.locationsMap = locationsMap;
        this.passengersMap = passengersMap;
    }

    @Override
    public List<Flight> readFromFile(String relativePath) {
        List<Flight> flights = new ArrayList<>();

        try {
            File file = new File(relativePath);
            if (!file.exists()) {
                System.err.println("Archivo no encontrado: " + relativePath);
                return flights;
            }

            String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                Flight flight = parseFlight(obj);
                flights.add(flight);
            }

        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error procesando JSON de vuelos: " + e.getMessage());
            e.printStackTrace();
        }

        return flights;
    }

    private Flight parseFlight(JSONObject obj) {
        String id = obj.getString("id");
        Plane plane = planesMap.get(obj.getString("plane"));
        Location departure = locationsMap.get(obj.getString("departureLocation"));
        Location arrival = locationsMap.get(obj.getString("arrivalLocation"));

        LocalDateTime departureDate = LocalDateTime.parse(obj.getString("departureDate"));
        int hoursArrival = obj.getInt("hoursDurationArrival");
        int minsArrival = obj.getInt("minutesDurationArrival");

        Location scale = null;
        int hoursScale = 0;
        int minsScale = 0;

        if (obj.has("scaleLocation") && !obj.isNull("scaleLocation")) {
            scale = locationsMap.get(obj.getString("scaleLocation"));
            hoursScale = obj.getInt("hoursDurationScale");
            minsScale = obj.getInt("minutesDurationScale");
        }

        Flight flight = (scale == null) ?
                new Flight(id, plane, departure, arrival, departureDate, hoursArrival, minsArrival) :
                new Flight(id, plane, departure, scale, arrival, departureDate, hoursArrival, minsArrival, hoursScale, minsScale);

        if (obj.has("passengers")) {
            JSONArray passengerIds = obj.getJSONArray("passengers");
            for (int i = 0; i < passengerIds.length(); i++) {
                long pid = passengerIds.getLong(i);
                Passenger p = passengersMap.get(pid);
                if (p != null) {
                    flight.addPassenger(p);
                    p.addFlight(flight);
                }
            }
        }

        return flight;
    }
}
