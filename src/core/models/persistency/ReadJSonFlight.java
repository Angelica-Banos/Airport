package core.models.persistency;

import core.models.Flight;
import core.models.Plane;
import core.models.Location;
import core.models.Passenger; // ¡NUEVO! Importar Passenger
import org.json.JSONArray; // ¡NUEVO! Importar JSONArray
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
    private Map<Long, Passenger> passengersMap; 
    
    public ReadJSonFlight(Map<String, Plane> planesMap, Map<String, Location> locationsMap, Map<Long, Passenger> passengersMap) {
        this.planesMap = planesMap;
        this.locationsMap = locationsMap;
        this.passengersMap = passengersMap; // Asignar el mapa de pasajeros
    }

    @Override
    public List<Flight> readFromFile(String relativePath) {
        List<Flight> flights = new ArrayList<>();

        try {
            File file = new File(relativePath);
            if (!file.exists()) {
                System.err.println("Archivo no encontrado: " + relativePath);
                return flights; // Devuelve una lista vacía si el archivo no existe
            }

            String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                String id = obj.getString("id");
                String planeId = obj.getString("plane");

                Plane plane = planesMap.get(planeId);
                if (plane == null) {
                    System.err.println("Avión no encontrado: " + planeId + " para el vuelo " + id);
                    continue; // Saltar este vuelo si el avión no se encuentra
                }

                String departureCode = obj.getString("departureLocation");
                String arrivalCode = obj.getString("arrivalLocation");

                Location departureLocation = locationsMap.get(departureCode);
                Location arrivalLocation = locationsMap.get(arrivalCode);

                if (departureLocation == null || arrivalLocation == null) {
                    System.err.println("Ubicación no encontrada para el vuelo " + id + ": " + departureCode + " o " + arrivalCode);
                    continue; // Saltar este vuelo si alguna ubicación no se encuentra
                }

                LocalDateTime departureDate;
                try {
                    departureDate = LocalDateTime.parse(obj.getString("departureDate"));
                } catch (DateTimeParseException e) {
                    System.err.println("Formato de fecha inválido para el vuelo " + id + ": " + obj.getString("departureDate"));
                    continue; // Saltar este vuelo si la fecha es inválida
                }

                int hoursDurationArrival = obj.getInt("hoursDurationArrival");
                int minutesDurationArrival = obj.getInt("minutesDurationArrival");

                // Verificar si hay escala en el JSON
                Location scaleLocation = null;
                int hoursDurationScale = 0;
                int minutesDurationScale = 0;

                if (obj.has("scaleLocation") && !obj.isNull("scaleLocation")) {
                    String scaleCode = obj.getString("scaleLocation");
                    scaleLocation = locationsMap.get(scaleCode);
                    if (scaleLocation == null) {
                        System.err.println("Ubicación de escala no encontrada para el vuelo " + id + ": " + scaleCode);
                        // Decide si continuar o no si la escala no se encuentra. Por ahora, asumimos que no hay escala.
                        scaleLocation = null;
                    } else {
                        hoursDurationScale = obj.getInt("hoursDurationScale");
                        minutesDurationScale = obj.getInt("minutesDurationScale");
                    }
                }

                Flight flight;
                if (scaleLocation == null) {
                    flight = new Flight(
                            id, plane, departureLocation, arrivalLocation,
                            departureDate, hoursDurationArrival, minutesDurationArrival
                    );
                } else {
                    flight = new Flight(
                            id, plane, departureLocation, scaleLocation, arrivalLocation,
                            departureDate, hoursDurationArrival, minutesDurationArrival,
                            hoursDurationScale, minutesDurationScale
                    );
                }

                // Lógica para cargar pasajeros desde el JSON del vuelo ---
                if (obj.has("passengers") && !obj.isNull("passengers")) {
                    JSONArray passengersJsonArray = obj.getJSONArray("passengers");
                    for (int j = 0; j < passengersJsonArray.length(); j++) {
                        long passengerId = passengersJsonArray.getLong(j);
                        Passenger foundPassenger = this.passengersMap.get(passengerId); // Buscar pasajero por ID

                        if (foundPassenger != null) {
                            flight.addPassenger(foundPassenger); // Añadir el objeto Passenger real al vuelo
                            foundPassenger.addFlight(flight); // Asegurar que el pasajero también referencia este vuelo
                        } else {
                            System.err.println("Advertencia (ReadJSonFlight): Pasajero con ID " + passengerId + " no encontrado para el vuelo " + flight.getId() + ". No se pudo vincular.");
                        }
                    }
                }
            

                flights.add(flight);
            }

        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
        } catch (Exception e) { // Captura cualquier otra excepción de parsing JSON
            System.err.println("Error procesando JSON de vuelos: " + e.getMessage());
            e.printStackTrace(); // Esto es muy útil para depurar errores de formato JSON
        }

        return flights;
    }
}