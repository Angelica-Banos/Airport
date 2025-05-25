package core.models;

import core.models.Flight;
import java.time.LocalDateTime;

public class FlightScheduler {

    public static LocalDateTime calculateArrivalDate(Flight flight) {
        return flight.getDepartureDate()
                .plusHours(flight.getHoursDurationScale())
                .plusMinutes(flight.getMinutesDurationScale())
                .plusHours(flight.getHoursDurationArrival())
                .plusMinutes(flight.getMinutesDurationArrival());
    }

    public static void delayFlight(Flight flight, int hours, int minutes) {
        flight.setDepartureDate(flight.getDepartureDate().plusHours(hours).plusMinutes(minutes));
    }
}
