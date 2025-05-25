/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.verifing;

/**
 *
 * @author Angie
 */
public class Verifying {

    private static final IdVerifier planeVerifier = new PlaneIdVerifier();
    private static final IdVerifier airportVerifier = new AirportIdVerifier();

    public static int verifyPlaneId(String id) {
        return planeVerifier.verify(id);
    }

    public static boolean checkAirportId(String id) {
        return airportVerifier.verify(id) == 0;
    }

}
