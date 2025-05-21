package core.models.persistency;

import core.models.Location;
import core.models.Passenger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReadJSonLocation  {

    public static List<Location> readFromFile(String relativePath) {
        List<Location> locations = new ArrayList<>();

        try {
            File file = new File(relativePath);
            if (!file.exists()) {
                System.err.println("Archivo no encontrado: " + relativePath);
                 if (locations instanceof Location) {
                return locations;}
            }

            String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
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


                locations.add(location);
            }

        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error procesando JSON: " + e.getMessage());
        }

        return locations;
    }

   

}
