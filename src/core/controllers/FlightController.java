package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.*; // Importar todas las clases de models
import core.models.storage.StorageFlights;
import core.models.storage.StorageLocations;
import core.models.storage.StoragePassengers;
import core.models.storage.StoragePlanes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList; // Asegúrate de importar ArrayList
import java.util.List;
import java.util.regex.Pattern;

public class FlightController {

    public static Response createfligth(String id, String planeId, String departureLocationId, String arrivalLocationId,
            String scaleLocationIdRaw, String yearStr, String monthStr, String dayStr,
            String hourStr, String minutesStr, String hoursDurationsArrivalStr,
            String minutesDurationsArrivalStr, String hoursDurationsScaleStr,
            String minutesDurationsScaleStr) {

        if (id == null || id.trim().isEmpty()) {
            return new Response("Flight ID must not be empty", Status.BAD_REQUEST);
        }
        id = id.trim(); // Limpiar el ID
        // Patrón de expresión regular para XXXYYY
        // ^      -> Inicio de la cadena
        // [A-Z]{3} -> Tres letras mayúsculas (A-Z)
        // [0-9]{3} -> Tres dígitos (0-9)
        // $      -> Fin de la cadena
        Pattern idPattern = Pattern.compile("^[A-Z]{3}[0-9]{3}$");
        if (!idPattern.matcher(id).matches()) {
            return new Response("Flight ID must follow the format 'XXXYYY' (e.g., 'ABC123'), where X are uppercase letters and Y are digits.", Status.BAD_REQUEST);
        }

        // Fechas y horas
        int year, month, day, hour, minutes;
        try {
            year = Integer.parseInt(yearStr.trim());
            month = Integer.parseInt(monthStr.trim());
            day = Integer.parseInt(dayStr.trim());
            hour = Integer.parseInt(hourStr.trim());
            minutes = Integer.parseInt(minutesStr.trim());
        } catch (NumberFormatException e) {
            return new Response("Invalid number format for date/time fields. Please enter valid numbers.", Status.BAD_REQUEST);
        }

        // Duraciones
        int hoursDurationsArrival, minutesDurationsArrival;
        int hoursDurationsScale = 0; // Inicializar a 0 para vuelos sin escala
        int minutesDurationsScale = 0; // Inicializar a 0 para vuelos sin escala

        try {
            hoursDurationsArrival = Integer.parseInt(hoursDurationsArrivalStr.trim());
            minutesDurationsArrival = Integer.parseInt(minutesDurationsArrivalStr.trim());
        } catch (NumberFormatException e) {
            return new Response("Invalid number format for arrival duration. Please enter valid numbers.", Status.BAD_REQUEST);
        }

        // Determinar si hay escala y parsear duraciones de escala
        // Considera si " " o "-" o "N/A_ID" son tus marcadores de "sin escala"
        boolean hasScale = (scaleLocationIdRaw != null && !scaleLocationIdRaw.trim().isEmpty()
                && !scaleLocationIdRaw.trim().equals("-") && !scaleLocationIdRaw.trim().equals("N/A_ID")
                && !scaleLocationIdRaw.trim().equals("Location")); // <-- ¡Aquí está el cambio clave!
        String scaleLocationId = scaleLocationIdRaw.trim(); // Limpiar para búsqueda

        if (hasScale) { // Solo intenta parsear si realmente hay una escala válida
            try {
                hoursDurationsScale = Integer.parseInt(hoursDurationsScaleStr.trim());
                minutesDurationsScale = Integer.parseInt(minutesDurationsScaleStr.trim());
            } catch (NumberFormatException e) {
                return new Response("Invalid number format for scale duration. Please enter valid numbers.", Status.BAD_REQUEST);
            }
        }

        StorageFlights storageFlights = StorageFlights.getInstance();
        List<Plane> allPlanes = StoragePlanes.getInstance().getAllAsList(); // Asume este método en StoragePlanes
        List<Location> allLocations = StorageLocations.getInstance().getAllAsList(); // Asume este método en StorageLocations

        Plane plane = null;
        for (Plane p : allPlanes) { // Usar la lista obtenida estáticamente
            if (planeId.equals(p.getId())) {
                plane = p;
                break;
            }
        }

        Location departure = null;
        Location arrival = null;
        Location scale = null; // Inicializar a null

        for (Location location : allLocations) { // Usar la lista obtenida estáticamente
            if (departureLocationId.equals(location.getAirportId())) {
                departure = location;
            }
            if (arrivalLocationId.equals(location.getAirportId())) {
                arrival = location;
            }
            if (hasScale && scaleLocationId.equals(location.getAirportId())) {
                scale = location;
            }
        }

        if (plane == null) {
            return new Response("Plane with ID " + planeId + " not found.", Status.BAD_REQUEST);
        }
        if (departure == null) {
            return new Response("Departure location with ID " + departureLocationId + " not found.", Status.BAD_REQUEST);
        }
        if (arrival == null) {
            return new Response("Arrival location with ID " + arrivalLocationId + " not found.", Status.BAD_REQUEST);
        }
        if (hasScale && scale == null) {
            return new Response("Scale location with ID " + scaleLocationId + " not found.", Status.BAD_REQUEST);
        }

        // Validar fecha de salida
        LocalDateTime departureDate;
        try {
            departureDate = LocalDateTime.of(year, month, day, hour, minutes);
        } catch (DateTimeParseException e) { // Usar DateTimeParseException para errores específicos de fecha/hora
            return new Response("Invalid departure date/time. Check month, day, or time values.", Status.BAD_REQUEST);
        } catch (Exception e) { // Captura cualquier otra excepción inesperada
            return new Response("Error processing departure date/time: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }

        if (departureDate.isBefore(LocalDateTime.now())) {
            return new Response("Departure date must be in the future", Status.BAD_REQUEST);
        }

        // Validar duraciones positivas
        if (hoursDurationsArrival < 0 || minutesDurationsArrival < 0) {
            return new Response("Arrival duration must be positive", Status.BAD_REQUEST);
        }
        if (hasScale && (hoursDurationsScale < 0 || minutesDurationsScale < 0)) {
            return new Response("Scale duration must be positive", Status.BAD_REQUEST);
        }

        // Verificar si el ID de vuelo ya existe
        if (storageFlights.exists(id)) {
            return new Response("Flight ID already exists", Status.BAD_REQUEST);
        }

        // Verificar conflictos de horario del avión
        LocalDateTime newStart = departureDate;
        long totalHours = hoursDurationsArrival + (hasScale ? hoursDurationsScale : 0);
        long totalMinutes = minutesDurationsArrival + (hasScale ? minutesDurationsScale : 0);
        LocalDateTime newEnd = departureDate.plusHours(totalHours).plusMinutes(totalMinutes);

        for (Flight existingFlight : plane.getFlights()) {
            LocalDateTime existingStart = existingFlight.getDepartureDate();
            LocalDateTime existingEnd = existingFlight.calculateArrivalDate(); // Asume que Flight.calculateArrivalDate() existe

            boolean overlap = newStart.isBefore(existingEnd) && existingStart.isBefore(newEnd);
            if (overlap) {
                return new Response("This plane has a schedule conflict with another flight (ID: " + existingFlight.getId() + ")", Status.BAD_REQUEST);
            }
        }

        // --- 6. Crear y almacenar el vuelo ---
        Flight newFlight;
        if (scale == null) { // Si no hay escala
            newFlight = new Flight(id, plane, departure, arrival, departureDate,
                    hoursDurationsArrival, minutesDurationsArrival);
        } else { // Si hay escala
            newFlight = new Flight(id, plane, departure, scale, arrival, departureDate,
                    hoursDurationsArrival, minutesDurationsArrival,
                    hoursDurationsScale, minutesDurationsScale);
        }

        storageFlights.add(newFlight); // Agrega el vuelo a la instancia del StorageFlights

        return new Response("Flight created successfully", Status.OK);
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

    public static Response delayFlight(String flightId, String hoursToDelayStr, String minutesToDelayStr) {

        if (flightId == null || flightId.trim().isEmpty()) {
            return new Response("Flight ID must not be empty.", Status.BAD_REQUEST);
        }
        flightId = flightId.trim();

        int hoursToDelay;
        int minutesToDelay;
        try {
            hoursToDelay = Integer.parseInt(hoursToDelayStr.trim());
            minutesToDelay = Integer.parseInt(minutesToDelayStr.trim());
        } catch (NumberFormatException e) {
            return new Response("Invalid number format for hours or minutes to delay. Please enter valid numbers.", Status.BAD_REQUEST);
        }

        if (hoursToDelay < 0 || minutesToDelay < 0) {
            return new Response("Delay duration must be positive.", Status.BAD_REQUEST);
        }

        if (hoursToDelay == 0 && minutesToDelay == 0) {
            return new Response("No delay specified (0 hours and 0 minutes).", Status.BAD_REQUEST);
        }

        StorageFlights storageFlights = StorageFlights.getInstance();
        Flight flightToDelay = storageFlights.get(flightId);

        if (flightToDelay == null) {
            return new Response("Flight with ID " + flightId + " not found.", Status.NOT_FOUND);
        }

        LocalDateTime currentDepartureDate = flightToDelay.getDepartureDate();
        LocalDateTime newDepartureDate = currentDepartureDate.plusHours(hoursToDelay).plusMinutes(minutesToDelay);

        if (newDepartureDate.isBefore(currentDepartureDate)) {

            return new Response("The new departure date cannot be before the current departure date.", Status.BAD_REQUEST);
        }

        Plane plane = flightToDelay.getPlane();
        LocalDateTime newStart = newDepartureDate;

        long totalHours = flightToDelay.getHoursDurationArrival() + flightToDelay.getHoursDurationScale();
        long totalMinutes = flightToDelay.getMinutesDurationArrival() + flightToDelay.getMinutesDurationScale();
        LocalDateTime newEnd = newDepartureDate.plusHours(totalHours).plusMinutes(totalMinutes);

        // Iterar sobre TODOS los vuelos del avión, *excepto el que estamos atrasando*.
        for (Flight existingFlight : plane.getFlights()) {
            if (!existingFlight.getId().equals(flightToDelay.getId())) { // Excluir el vuelo actual
                LocalDateTime existingStart = existingFlight.getDepartureDate();
                LocalDateTime existingEnd = existingFlight.calculateArrivalDate();

                boolean overlap = newStart.isBefore(existingEnd) && existingStart.isBefore(newEnd);
                if (overlap) {
                    return new Response("Delaying this flight causes a schedule conflict with another flight (ID: " + existingFlight.getId() + ") for the plane " + plane.getId(), Status.BAD_REQUEST);
                }
            }
        }

        flightToDelay.setDepartureDate(newDepartureDate);

        if (!storageFlights.update(flightToDelay)) {
            return new Response("Failed to update flight in storage.", Status.INTERNAL_SERVER_ERROR);
        }

        return new Response("Flight " + flightId + " successfully delayed to "
                + newDepartureDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + ".", Status.OK);
    }

    public static Response addPassengerToFlight(String flightId, String passengerId) {

        if (flightId == null || flightId.trim().isEmpty()) {
            return new Response("El ID del vuelo no puede estar vacío.", Status.BAD_REQUEST);
        }
        if (passengerId == null || passengerId.trim().isEmpty()) {
            return new Response("El ID del pasajero no puede estar vacío.", Status.BAD_REQUEST);
        }
        try {
            Flight flight = StorageFlights.getInstance().get(flightId);
            if (flight == null) {
                return new Response("Vuelo no encontrado con el ID: " + flightId, Status.NOT_FOUND);
            }

            Passenger passenger = StoragePassengers.getInstance().get(passengerId);
            if (passenger == null) {
                return new Response("Pasajero no encontrado con el ID: " + passengerId, Status.NOT_FOUND);
            }

            // Accedemos a la lista de pasajeros del vuelo directamente.
            List<Passenger> passengersInFlight = flight.getPassengers();
            if (passengersInFlight != null && passengersInFlight.contains(passenger)) {
                return new Response("El pasajero ya está en este vuelo.", Status.BAD_REQUEST);
            }
            
            //Revisar la capacidad
            if(flight.getNumPassengers()>=flight.getPlane().getMaxCapacity()){
                return new Response("The plane is at max .", Status.BAD_REQUEST);
            }

            // Llamamos al método addPassenger de la instancia de Flight.
            flight.addPassenger(passenger);
            passenger.addFlight(flight);

            // Guardar el cambio del pasajero en el StoragePassengers
            StoragePassengers.getInstance().update(passenger);

            StorageFlights.getInstance().update(flight);

            return new Response("Pasajero " + passenger.getFullname() + " añadido al vuelo " + flight.getId() + " exitosamente.", Status.OK);

        } catch (Exception e) {
            System.err.println("Error al añadir pasajero al vuelo: " + e.getMessage());
            return new Response("Error interno del servidor al añadir pasajero al vuelo.", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
