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
public class ReadJSonLocation implements ReadJSon {

    @Override
    public ArrayList<String> read(String ruta) {
        ruta = "locations.json";
        ArrayList<String> result = new ArrayList<>();
        try ( FileReader reader = new FileReader(ruta)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONArray locationsArray = new JSONArray(tokener);

            for (int i = 0; i < locationsArray.length(); i++) {
                JSONObject location = locationsArray.getJSONObject(i);
                String info = "Location ID: " + location.getString("airportId")
                        + ", Name: " + location.getString("airportName")
                        + ", City: " + location.getString("airportCity")
                        + ", Country: " + location.getString("airportCountry")
                        + ", Latitude: " + location.getDouble("airportLatitude")
                        + ", Longitude: " + location.getDouble("airportLongitude");
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
