/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Location;
import java.util.ArrayList;

/**
 *
 * @author Angie
 */
public class StorageLocations implements Storage<Location>{
  private static StorageLocations instance;
  private ArrayList<Location> locations;
  
    private StorageLocations(){
        instance = new StorageLocations();
        this.locations = new ArrayList<>();
    }
    
    public static StorageLocations getInstance() {
        if(instance == null){
            instance = new StorageLocations();
        }
        return instance;
    }

    @Override
    public boolean add(Location location) {
        Location lc = this.get(location.getAirportId());
         if(lc != null){
                return false;
            }
        locations.add(location);
        return true;
    }

    @Override
    public boolean delete() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Location get(String id) {
       for (Location lc : locations){
            if(lc.getAirportId().equals(id)){
                return lc;
            }
        }
        return null;
    }
}
