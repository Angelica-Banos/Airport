/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Plane;
import java.util.ArrayList;

/**
 *
 * @author Angie
 */
public class StoragePlanes implements Storage{
  private static StoragePlanes instance;
  private ArrayList<Plane> planes;
  
  
    private StoragePlanes(){
        this.planes = new ArrayList<>();
    }
    
    public static StoragePlanes getInstance() {
        if(instance == null){
            instance = new StoragePlanes();
        }
        return instance;
    }
    @Override
    public boolean add() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Plane get(String id) {
        for (Plane pl : planes){
            if(pl.getId().equals(id)){
                return pl;
            }
        }
        return null;
    }


    
}
