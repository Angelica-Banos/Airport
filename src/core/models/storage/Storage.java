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
    boolean add(T meow); 
    boolean delete(); 
    boolean update(); 
    T get(String id); 
    
}
