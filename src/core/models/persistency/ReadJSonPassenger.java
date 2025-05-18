/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.persistency;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author Angie
 */
public class ReadJSonPassenger implements ReadJSon {

    @Override
    public ArrayList<String> read(String ruta) {
        ruta = "passengers.json";
        ArrayList<String> result = new ArrayList<>();
        try ( FileReader reader = new FileReader(ruta)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONArray passengersArray = new JSONArray(tokener);

            for (int i = 0; i < passengersArray.length(); i++) {
                JSONObject passenger = passengersArray.getJSONObject(i);
                String info = "ID: " + passenger.getInt("id")
                        + ", firstname: " + passenger.getString("firstname")
                        + ", lastname: " + passenger.getString("lastname")
                        + ", birthDate: " + passenger.getString("birthDate")
                        + ", countryPhoneCode: " + passenger.getInt("countryPhoneCode")
                        + ", phone: " + passenger.getInt("phone") 
                        + ", Country: " + passenger.getString("country");
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
