/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models;

/**
 *
 * @author isabc
 */
public class AirportIdValidator implements Validator<String> {

    @Override
    public boolean isValid(String id) {
        return id != null && id.length() == 3
                && Character.isUpperCase(id.charAt(0))
                && Character.isUpperCase(id.charAt(1))
                && Character.isUpperCase(id.charAt(2));
    }

}

