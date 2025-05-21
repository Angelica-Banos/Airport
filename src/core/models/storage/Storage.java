/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

/**
 *
 * @author Angie
 */
public interface Storage<T> {  
    T get(String id);
    boolean add(Object obj);
    boolean delete(String id);
    boolean update(Object obj);
    public boolean loadFromJson(String filepath);
    
    
}
