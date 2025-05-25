package core.models.observers;

import core.controllers.update.UpdateUsers;
import core.models.Passenger;

public class PassengerObserver implements Observer<Passenger>{

    @Override
    public void update(Passenger passenger) {
        UpdateUsers.getUpdateUsers().newPassengerMade(passenger);
    }
    
}
