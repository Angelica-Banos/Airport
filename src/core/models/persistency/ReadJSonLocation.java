package core.models.persistency;

import core.models.Location;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ReadJSonLocation implements Reader<Location> {

    @Override
    public List<Location> readFromFile(String relativePath) {
        List<Location> locations = new ArrayList<>();

        try {
            File file = new File(relativePath);
            if (!file.exists()) {
                System.err.println("Archivo no encontrado: " + relativePath);
                return locations;
            }

            String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Location location = parseLocation(obj);
                locations.add(location);
            }

        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error procesando JSON: " + e.getMessage());
        }

        return locations;
    }

    private Location parseLocation(JSONObject obj) {
        String id = obj.getString("airportId");
        String name = obj.getString("airportName");
        String city = obj.getString("airportCity");
        String country = obj.getString("airportCountry");
        long lat = obj.getLong("airportLatitude");
        long lon = obj.getLong("airportLongitude");

        return new Location(id, name, city, country, lat, lon);
    }
}