/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.verifing;

import core.models.Flight;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author isabc
 */
public class NewClassFlightManager {
    
private final List<Flight> flights = new ArrayList<>();

public void addFlight(Flight flight) {
if (flight != null && !flights.contains(flight)) {
flights.add(flight);
}
}

public boolean removeFlight(Flight flight) {
 return flights.remove(flight);
 }

 public List<Flight> getFlights() {
 return new ArrayList<>(flights);
 }

 public int getNumFlights() {
 return flights.size();
 }

}
