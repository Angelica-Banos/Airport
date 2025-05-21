package core.models.persistency;

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

public class ReadJSONPassenger  {

    
    public static List<Passenger> readFromFile(String relativePath) {
        List<Passenger> passengers = new ArrayList<>();

        try {
            File file = new File(relativePath);
            if (!file.exists()) {
                System.err.println("Archivo no encontrado: " + relativePath);
                return passengers;
            }

            String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                long id = obj.getLong("id");
                String firstname = obj.getString("firstname");
                String lastname = obj.getString("lastname");
                LocalDate birthDate = LocalDate.parse(obj.getString("birthDate"));
                int countryPhoneCode = obj.getInt("countryPhoneCode");
                long phone = obj.getLong("phone");
                String country = obj.getString("country");

                Passenger passenger = new Passenger(
                        id, firstname, lastname, birthDate, countryPhoneCode, phone, country
                );

                passengers.add(passenger);
            }

        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error procesando JSON: " + e.getMessage());
        }

        return passengers;
    }
}
