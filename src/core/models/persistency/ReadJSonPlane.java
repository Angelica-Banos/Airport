/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.persistency;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author Angie
 */
public class ReadJSonPlane implements ReadJSon {

    @Override
    public ArrayList<String> read(String ruta) {
        ruta = "planes.json";
        ArrayList<String> result = new ArrayList<>();
        try ( FileReader reader = new FileReader(ruta)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONArray planesArray = new JSONArray(tokener);

            for (int i = 0; i < planesArray.length(); i++) {
                JSONObject plane = planesArray.getJSONObject(i);
                String info = "ID: " + plane.getString("id")
                        + ", brand: " + plane.getString("brand")
                        + ", model: " + plane.getString("model")
                        + ", maxCapacity: " + plane.getInt("maxCapacity")
                        + ", airline: " + plane.getString("airline");
                result.add(info);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al procesar el archivo JSON: " + e.getMessage());
        }
        return result;
    }
}

