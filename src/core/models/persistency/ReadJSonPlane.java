/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.persistency;

import core.models.Plane;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author Angie
 */
public class ReadJSonPlane implements ReadJSon<Plane> {

    @Override
    public List<Plane> readFromFile(String relativePath) {
        List<Plane> planes = new ArrayList<>();
        try {
            File file = new File(relativePath);
            if (!file.exists()) {
                System.err.println("Archivo no encontrado: " + relativePath);
                if (planes instanceof Plane) {
                    return planes;
                }
            }
            String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                String id = obj.getString("id");
                String brand = obj.getString("brand");
                String model = obj.getString("model");
                int maxCapacity = obj.getInt("maxCapacity");
                String airline = obj.getString("airline");

                Plane plane = new Plane(
                        id, brand, model, maxCapacity, airline
                );

                planes.add(plane);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error procesando JSON: " + e.getMessage());
        }

        return planes;
    }
}
