package core.models.persistency;

import core.models.Flight;
import core.models.Plane;
import core.models.Location;
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

public class ReadJSonFlight implements ReadJSon<Flight> {

    private Map<String, Plane> planesMap;
    private Map<String, Location> locationsMap;

    public ReadJSonFlight(Map<String, Plane> planesMap, Map<String, Location> locationsMap) {
        this.planesMap = planesMap;
        this.locationsMap = locationsMap;
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

                String id = obj.getString("id");
                String planeId = obj.getString("plane");

                Plane plane = planesMap.get(planeId);
                if (plane == null) {
                    System.err.println("Avión no encontrado: " + planeId);
                    continue;
                }

                String departureCode = obj.getString("departureLocation");
                String arrivalCode = obj.getString("arrivalLocation");

                Location departureLocation = locationsMap.get(departureCode);
                Location arrivalLocation = locationsMap.get(arrivalCode);

                if (departureLocation == null || arrivalLocation == null) {
                    System.err.println("Ubicación no encontrada: " + departureCode + " o " + arrivalCode);
                    continue;
                }

                LocalDateTime departureDate;
                try {
                    departureDate = LocalDateTime.parse(obj.getString("departureDate"));
                } catch (DateTimeParseException e) {
                    System.err.println("Formato de fecha inválido: " + obj.getString("departureDate"));
                    continue;
                }

                int hoursDurationArrival = obj.getInt("hoursDurationArrival");
                int minutesDurationArrival = obj.getInt("minutesDurationArrival");

                Flight flight = new Flight(
                        id, plane, departureLocation, arrivalLocation,
                        departureDate, hoursDurationArrival, minutesDurationArrival
                );

                flights.add(flight);
            }

        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error procesando JSON: " + e.getMessage());
        }

        return flights;
    }
}
