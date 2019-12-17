package by.epam.courierPicker.validation;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

public class RegisterValidationTest {

    @Test
    public void testLoginValidation() {
        Map<String, String> req = new HashMap();
        assertEquals(RegisterValidation.loginValidation("mishka11"), req);
    }

    @Test
    public void testEmailValidation() {
        Map<String, String> req = new HashMap();
        assertEquals(RegisterValidation.emailValidation("mishka@gmail.com"), req);
    }

    @Test
    public void testFirstnameValidation() {
        Map<String, String> req = new HashMap();
        assertEquals(RegisterValidation.firstnameValidation("Misha"), req);
    }

    @Test
    public void testLastnameValidation() {
        Map<String, String> req = new HashMap();
        assertEquals(RegisterValidation.lastnameValidation("Kolesnik"), req);
    }

    @Test
    public void testPasswordValidation() {
        Map<String, String> req = new HashMap();
        assertEquals(RegisterValidation.passwordValidation("Masterdota12"), req);
    }
}