/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Passenger;
import core.models.storage.StorageFlights;
import core.models.storage.StoragePassengers;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Angie
 */
public class PassengerController {

    public static Response createPassanger(String id, String firstname, String lastname, String year, String month, String day, String countryPhoneCode, String phone, String country) {
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
            if (firstname.trim().equals("")) {
                return new Response("First name must not be empty", Status.BAD_REQUEST);
            }

            if (lastname.trim().equals("")) {
                return new Response("Last name must not be empty", Status.BAD_REQUEST);
            }

            if (countryPhoneCode.trim().equals("")) {
                return new Response("Country phone code must not be empty", Status.BAD_REQUEST);
            }
            if (phone.trim().equals("")) {
                return new Response("Phone number must not be empty", Status.BAD_REQUEST);
            }
            if (country.trim().equals("")) {
                return new Response("Country must not be empty", Status.BAD_REQUEST);
            }

            // Validar  que el año, mes y días sean números
            int intYear, intMonth, intDay;
            try {
                intYear = Integer.parseInt(year);
            } catch (NumberFormatException ex) {
                return new Response("The birth year must be a number", Status.BAD_REQUEST);
            }
            try {
                intMonth = Integer.parseInt(month);
            } catch (NumberFormatException ex) {
                return new Response("The birth month must be a number", Status.BAD_REQUEST);
            }
            try {
                intDay = Integer.parseInt(day);
            } catch (NumberFormatException ex) {
                return new Response("The birth day must be a number", Status.BAD_REQUEST);
            }
            //Validar que sea el año, mes y días sean enteros positivos
            if (!(intYear >= 0)) {
                return new Response("The birth year must be positive", Status.BAD_REQUEST);
            }
            if (!(intMonth >= 0)) {
                return new Response("The birth month must be positive", Status.BAD_REQUEST);
            }
            if (!(intDay >= 0)) {
                return new Response("The birth day must be positive. (how???)", Status.BAD_REQUEST);
            }
            //Crear Birthdate
            LocalDate birthDate;
            birthDate = LocalDate.of(intYear, intMonth, intDay);
            //  Validar que birthDate no sea en el futuro
            if (birthDate == null) {
                return new Response("Birth date must not be null", Status.INTERNAL_SERVER_ERROR);
            }
            if (birthDate.isAfter(LocalDate.now())) {
                return new Response("Birth date cannot be in the future", Status.INTERNAL_SERVER_ERROR);
            }
            try {
                //Verify length of phone
                if (phone.trim().length() < 1 || phone.trim().length() > 11) {
                    return new Response("The length of the phone number must be between 1 and 11 numbers", Status.BAD_REQUEST);
                }

                //Verify that phone is number
                int intPhone;
                try {
                    intPhone = Integer.parseInt(phone);
                } catch (NumberFormatException ex) {
                    return new Response("The phone number must be a number", Status.BAD_REQUEST);
                }
                //Verify numbers in the phone
                if (!(intPhone >= 0)) {
                    return new Response("The phone number must be positive.", Status.BAD_REQUEST);
                }
            } catch (Exception e) {
                return new Response("Internal Server Error", Status.INTERNAL_SERVER_ERROR);
            }

            //Verify country phone code is number
            int intCountryPhoneCode;
            try {
                intCountryPhoneCode = Integer.parseInt(countryPhoneCode);
            } catch (NumberFormatException ex) {
                return new Response("The country phone code must be a number", Status.BAD_REQUEST);
            }

            //Verify length of country phone code
            if (countryPhoneCode.trim().length() < 1 || countryPhoneCode.trim().length() > 3) {
                return new Response("The length of the country phone code must be between 1 and 3 numbers", Status.BAD_REQUEST);
            }

            //Verify numbers in the phone
            if (!(intCountryPhoneCode >= 0)) {
                return new Response("The country phone code must be positive.", Status.BAD_REQUEST);
            }

            //Verify that the id isn't taken
            if (!addPassenger(new Passenger(intId, firstname, lastname, birthDate, intCountryPhoneCode, intId, country))) {
                return new Response("There's already a passenger with that Id", Status.BAD_REQUEST);
            }

            //All good
            return new Response("Passenger created successfully", Status.CREATED);

        } catch (Exception e) {
            return new Response("Internal Server Error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static boolean addPassenger(Passenger passenger) {
        StoragePassengers storagePlassengers = StoragePassengers.getInstance();
        return storagePlassengers.add(passenger);
    }


    public static List<Object[]> getPassengerTableData() {
        StoragePassengers storage = StoragePassengers.getInstance();
        List<Object[]> tableData = new ArrayList<>();

        for (Passenger p : storage.getAll()) {
            Object[] row = {
                p.getId(),
                p.getFullname(),
                p.getBirthDate(),
                p.calculateAge(),
                p.generateFullPhone(),
                p.getCountry(),
                p.getNumFlights()
            };
            tableData.add(row);
        }

        return tableData;
    }


}
