/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.storage.StorageFlights;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author Angie
 */
public class PassengerController {

    public static Response createPassanger(String id, String firstname, String lastname, LocalDate birthDate, String countryPhoneCode, String phone, String country) {
        try {
            StorageFlights storage = StorageFlights.getInstance();

            //Verify that the id isn't empty
            if (id.trim().isEmpty()) {
                return new Response("Id can't be empty", Status.BAD_REQUEST);
            }

            //Verify that Id is a whole number (int)
            int intId;
            try {
                intId = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                return new Response("Id must be a whole number", Status.BAD_REQUEST);
            }

            //Verify lenght of id
            String ids = String.valueOf(id);
            if (ids.length() < 1 || ids.length() > 15) {
                return new Response("The length of the Id must be between 1 and 15 digits", Status.BAD_REQUEST);
            }
            //Verify numbers in the id
            if (!(intId >= 0)) {
                return new Response("The Id must be a positive number", Status.BAD_REQUEST);
            }

            //Verify that the string fields are not empty
            if(firstname.trim().equals("")){
                return new Response("First name must not be empty", Status.BAD_REQUEST);
            }
            
            if(lastname.trim().equals("")){
                return new Response("Last name must not be empty", Status.BAD_REQUEST);
            }
            if (country.trim().equals("")) {
                return new Response("Country must not be empty", Status.BAD_REQUEST);
            } 
            if (countryPhoneCode.trim().equals("")) {
                 return new Response("Country phone code must not be empty", Status.BAD_REQUEST);
            }
            if (phone.trim().equals("")) {
                 return new Response("Phone number must not be empty", Status.BAD_REQUEST);
            }
           

            try {
                //Verify length of countryPhoneCode
                if (phone.length() < 1 || phone.length() > 11) {
                    return new Response("The length of the phone number must be between 1 and 11 numbers", Status.BAD_REQUEST);
                }
                try {
                    int intPhone = Integer.parseInt(phone);
                } catch (NumberFormatException ex) {
                }
                //Verify numbers in the countryPhoneCode
                if (!(phone >= 0)) {
                    return new Response("The Id must be non negative", Status.BAD_REQUEST);
                }
            } catch (Exception e) {
                return new Response("Internal Server Error", Status.INTERNAL_SERVER_ERROR);
            }

            try {
                //  Validar que birthDate no sea null ni en el futuro
                if (birthDate == null) {
                    return new Response("Birth date must not be null", Status.BAD_REQUEST);
                }
                if (birthDate.isAfter(LocalDate.now())) {
                    return new Response("Birth date cannot be in the future", Status.BAD_REQUEST);
                }
            } catch (Exception e) {
                return new Response("Error validating birth date", Status.INTERNAL_SERVER_ERROR);
            }

            try {
                //Verify lenght of phone
                String c = String.valueOf(countryPhoneCode);
                if (c.length() < 1 || c.length() > 3) {
                    return new Response("The length of the Id must be between 1 and 3 numbers", Status.BAD_REQUEST);
                }
                //Verify numbers in the phone
                if (!(countryPhoneCode >= 0)) {
                    return new Response("The Id must be non negative", Status.BAD_REQUEST);
                }
            } catch (Exception e) {
                return new Response("Internal Server Error", Status.INTERNAL_SERVER_ERROR);
            }
            return new Response("Passenger created successfully", Status.OK);

        } catch (Exception e) {
            return new Response("Internal Server Error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
