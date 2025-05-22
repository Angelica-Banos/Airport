/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.models.Plane;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author isabc
 */
public class PlaneControllerTest {
    
    public PlaneControllerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of createPlane method, of class PlaneController.
     */
    @Test
    public void testCreatePlane() {
        System.out.println("createPlane");
        String id = "";
        String brand = "";
        String model = "";
        String maxCapacity = "";
        String airline = "";
        Response expResult = null;
        Response result = PlaneController.createPlane(id, brand, model, maxCapacity, airline);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPlane method, of class PlaneController.
     */
    @Test
    public void testAddPlane() {
        System.out.println("addPlane");
        Plane plane = null;
        boolean expResult = false;
        boolean result = PlaneController.addPlane(plane);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fillStorage method, of class PlaneController.
     */
    @Test
    public void testFillStorage() {
        System.out.println("fillStorage");
        boolean expResult = false;
        boolean result = PlaneController.fillStorage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
