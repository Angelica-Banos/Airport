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
public class ReadJSonFlight implements ReadJSon {

    @Override
    public ArrayList<String> read(String ruta) {
        ruta = "flights.json";
        ArrayList<String> result = new ArrayList<>();
        try ( FileReader reader = new FileReader(ruta)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONArray flightsArray = new JSONArray(tokener);

            for (int i = 0; i < flightsArray.length(); i++) {
                JSONObject flight = flightsArray.getJSONObject(i);
                String info = "Fligth ID: " + flight.getString("id")
                        + ", Plane: " + flight.getString("plane")
                        + ", From: " + flight.getString("departureLocation")
                        + ", To: " + flight.getString("arrivalLocation")
                        + ", Departure: " + flight.getString("departureDate")
                        + ", Duration: " + flight.getInt("hoursDurationArrival") + "h "
                        + flight.getInt("minutesDurationArrival") + "min";
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
