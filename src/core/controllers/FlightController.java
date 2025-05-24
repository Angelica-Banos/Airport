package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.*;
import core.models.storage.StorageFlights;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightController {

    public static Response createfligth(String id, Plane plane, Location departureLocation, Location scaleLocation,
            Location arrivalLocation, LocalDateTime departureDate,
            int hoursDurationArrival, int minutesDurationArrival,
            int hoursDurationScale, int minutesDurationScale) {
        try {
            //Verify id
            if (id == null || id.trim().isEmpty()) {
                return new Response("Flight ID must not be empty", Status.BAD_REQUEST);
            }
            //Verify that plane exist
            if (plane == null) {
                return new Response("Plane must not be null", Status.BAD_REQUEST);
            }
            //Verify departure and arrival
            if (departureLocation == null || arrivalLocation == null) {
                return new Response("Departure and Arrival locations must not be null", Status.BAD_REQUEST);
            }
            //Verify that the departure date exist
            if (departureDate == null || departureDate.isBefore(LocalDateTime.now())) {
                return new Response("Departure date must be in the future", Status.BAD_REQUEST);
            }
            //Verify hours and minutes
            if (hoursDurationArrival < 0 || minutesDurationArrival < 0) {
                return new Response("Arrival duration must be positive", Status.BAD_REQUEST);
            }

            if (scaleLocation != null && (hoursDurationScale < 0 || minutesDurationScale < 0)) {
                return new Response("Scale duration must be positive", Status.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new Response("Internal Server Error", Status.INTERNAL_SERVER_ERROR);
        }

        try {
            StorageFlights storage = StorageFlights.getInstance();

            if (storage.exists(id)) {
                return new Response("Flight ID already exists", Status.BAD_REQUEST);
            }

            LocalDateTime newStart = departureDate;

            long totalHours = hoursDurationArrival + hoursDurationScale;
            long totalMinutes = minutesDurationArrival + minutesDurationScale;

            LocalDateTime newEnd = departureDate.plusHours(totalHours).plusMinutes(totalMinutes);

            for (Flight existingFlight : plane.getFlights()) {
                LocalDateTime existingStart = existingFlight.getDepartureDate();
                LocalDateTime existingEnd = existingFlight.calculateArrivalDate();

                //If fligths are conflicted
                boolean overlap = newStart.isBefore(existingEnd) && existingStart.isBefore(newEnd);
                if (overlap) {
                    return new Response("This plane has a schedule conflict with another flight", Status.BAD_REQUEST);
                }
            }

            Flight newFlight;
            if (scaleLocation != null) {
                newFlight = new Flight(id, plane, departureLocation, scaleLocation, arrivalLocation,
                        departureDate, hoursDurationArrival, minutesDurationArrival,
                        hoursDurationScale, minutesDurationScale);
            } else {
                newFlight = new Flight(id, plane, departureLocation, scaleLocation, arrivalLocation, departureDate, hoursDurationArrival, minutesDurationArrival, hoursDurationScale, minutesDurationScale);

            }

        } catch (Exception e) {
            return new Response("Internal Server Error", Status.INTERNAL_SERVER_ERROR);
        }
        return new Response("Fligth created successfully", Status.OK);
    }

    
    
    public static List<Object[]> getFlightTableData() {
        StorageFlights storage = StorageFlights.getInstance();
        List<Object[]> tableData = new ArrayList<>();

        for (Flight flight : storage.getAllAsList()) {
            Object[] row = {
                flight.getId(),
                flight.getPlane().getId(),
                flight.getDepartureLocation().getAirportId(),
                flight.getArrivalLocation().getAirportId(),
                (flight.getScaleLocation() != null ? flight.getScaleLocation().getAirportId() : "N/A"),
                flight.getDepartureDate().toString(),
                flight.calculateArrivalDate().toString(),
                flight.getNumPassengers()
            };
            tableData.add(row);
        }

        return tableData;
    }

  

}
