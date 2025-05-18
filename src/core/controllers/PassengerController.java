/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author Angie
 */
public class PassengerController {

    public Response createPassanger(long id, String firstname, String lastname, LocalDate birthDate, int countryPhoneCode, long phone, String country) {
        try {
            //Verify lenght of id
            String ids = String.valueOf(id);
            if (ids.length() < 1 || ids.length() > 15) {
                return new Response("The length of the Id must be between 1 and 15 numbers", Status.BAD_REQUEST);
            }
            //Verify numbers in the id
            if (!(id >= 0)) {
                return new Response("The Id must be non negative", Status.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new Response("Internal Server Error", Status.INTERNAL_SERVER_ERROR);
        }

        try {
            //Verify that the string fields are not empty
            if (firstname.length() < 1 | lastname.length() < 1 | country.length() < 1) {
                return new Response("Fields must not be empty", Status.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new Response("Internal Server Error", Status.INTERNAL_SERVER_ERROR);
        }

        try {
            //Verify lenght of countryPhoneCode
            String p = String.valueOf(phone);
            if (p.length() < 1 || p.length() > 11) {
                return new Response("The length of the Id must be between 1 and 11 numbers", Status.BAD_REQUEST);
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

    }
}
