/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models;

/**
 *
 * @author isabc
 */
import core.models.Flight;
import core.models.Passenger;
import java.util.ArrayList;
//Clase paramanejar los pasajeros de fligths
public class PassengerManifest {

    private final Flight flight;
    private final ArrayList<Passenger> passengers;

    public PassengerManifest(Flight flight) {
        this.flight = flight;
        this.passengers = new ArrayList<>();
    }

    public void addPassenger(Passenger passenger) {
        if (passenger != null && !passengers.contains(passenger)) {
            passengers.add(passenger);
        }
    }

    public boolean removePassenger(Passenger passenger) {
        return passengers.remove(passenger);
    }

    public int getPassengerCount() {
        return passengers.size();
    }

    public ArrayList<Passenger> getPassengers() {
        return new ArrayList<>(passengers);
    }
}

