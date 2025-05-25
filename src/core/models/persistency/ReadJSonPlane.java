package core.models.persistency;

import core.models.Plane;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ReadJSonPlane implements Reader<Plane> {

    @Override
    public List<Plane> readFromFile(String relativePath) {
        List<Plane> planes = new ArrayList<>();

        try {
            File file = new File(relativePath);
            if (!file.exists()) {
                System.err.println("Archivo no encontrado: " + relativePath);
                return planes;
            }

            String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Plane plane = parsePlane(obj);
                planes.add(plane);
            }

        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error procesando JSON: " + e.getMessage());
        }

        return planes;
    }

    private Plane parsePlane(JSONObject obj) {
        String id = obj.getString("id");
        String brand = obj.getString("brand");
        String model = obj.getString("model");
        int capacity = obj.getInt("maxCapacity");
        String airline = obj.getString("airline");

        return new Plane(id, brand, model, capacity, airline);
    }
}