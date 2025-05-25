/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.verifing;

/**
 *
 * @author isabc
 */

public class PlaneIdVerifier implements IdVerifier {

    @Override
    public int verify(String id) {
        if (id.length() < 7) return 3; // longitud mínima

        String letters = id.substring(0, 2);
        String numbers = id.substring(id.length() - 5);

        if (!(Character.isUpperCase(letters.charAt(0)) && Character.isUpperCase(letters.charAt(1)))) {
            return 2; // Las letras no están en mayúscula
        }

        try {
            Integer.parseInt(numbers);
        } catch (NumberFormatException e) {
            return 1; // No termina en 5 números
        }

        return 0; // Correcto
    }
}

