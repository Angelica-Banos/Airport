/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.verifing;

/**
 *
 * @author isabc
 */
public class AirportIdVerifier implements IdVerifier {

    @Override
    public int verify(String id) {
        return (id.length() == 3
                && Character.isUpperCase(id.charAt(0))
                && Character.isUpperCase(id.charAt(1))
                && Character.isUpperCase(id.charAt(2))) ? 0 : 1;
    }
}
