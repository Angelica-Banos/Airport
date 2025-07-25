/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import java.util.List;

/**
 *
 * @author Angie
 */
public interface Storage<T> {  
    T get(String id);
    boolean add(T obj);
    boolean delete(String id);
    boolean update(T obj);
    public boolean loadFromJson(String filepath);
    public void notifyObserver(T obj);
    public List<T> getList();
}
