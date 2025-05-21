/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.persistency;

import core.models.Passenger;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Angie
 */
public interface ReadJSon {
    List<Object> readFromFile(String relativePath);
}
