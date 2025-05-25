package core.models;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List; // Es preferible usar la interfaz List

/**
 *
 * @author edangulo
 */
public class Passenger implements Clonable<Passenger> {

    private long id; 
    private String firstname;
    private String lastname;
    private LocalDate birthDate;
    private int countryPhoneCode;
    private long phone;
    private String country;
    private ArrayList<Flight> flights;

    public Passenger(long id, String firstname, String lastname, LocalDate birthDate, int countryPhoneCode, long phone, String country) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.countryPhoneCode = countryPhoneCode;
        this.phone = phone;
        this.country = country;
        this.flights = new ArrayList<>(); 
    }

    public void addFlight(Flight flight) {
        if (flight != null && !this.flights.contains(flight)) {
            this.flights.add(flight);
        }
    }

    public boolean removeFlight(Flight flight) {
        return this.flights.remove(flight);
    }


    
    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public int getCountryPhoneCode() {
        return countryPhoneCode;
    }

    public long getPhone() {
        return phone;
    }

    public String getCountry() {
        return country;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setCountryPhoneCode(int countryPhoneCode) {
        this.countryPhoneCode = countryPhoneCode;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public String getFullname() {
        return firstname + " " + lastname;
    }

    public String generateFullPhone() {
        return "+" + countryPhoneCode + " " + phone;
    }

    public int calculateAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public int getNumFlights() {
        return flights.size();
    }

    @Override
    public Passenger clone() {
        Passenger clonePassenger = new Passenger(
            this.id,
            this.firstname,
            this.lastname,
            this.birthDate,
            this.countryPhoneCode,
            this.phone,
            this.country
        );
        // Clona la lista de vuelos también para el clon del pasajero.
        // Se añaden las mismas referencias de objetos Flight, no se clonan los Flights en sí.
        for (Flight flight : this.flights) {
            clonePassenger.addFlight(flight);
        }
        return clonePassenger;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Si es la misma instancia, son iguales
        if (obj == null || getClass() != obj.getClass()) return false; // Si es nulo o de diferente clase, no son iguales
        Passenger passenger = (Passenger) obj; // Castear al tipo Passenger
        return id == passenger.id; // La igualdad se basa en el ID único del pasajero
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id); // Genera un hash basado en el ID, consistente con equals
    }
}