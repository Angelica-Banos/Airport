package core.models.observers;

import core.controllers.update.Update;
import core.models.Passenger;

public class PassengerObserver implements Observer<Passenger>{
   Update  passengerHandler;
   
    public PassengerObserver(Update passengerObserver) {
        this.passengerHandler = passengerObserver;
    }
    @Override
    public void update(Passenger passenger) {
        passengerHandler.newObject(passenger);
    }
    
}
