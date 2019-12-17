package by.epam.courierPicker.logic;

import by.epam.courierPicker.exception.LogicException;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CourierOfferLogicTest {

    @Test
    public void testAddCourierOffer() {
        try {
            assertEquals(CourierOfferLogic.INSTANCE.addCourierOffer(new String[]{"car"}, new String[]{"food"}, 1, "test"), true);
        } catch (LogicException e) {

        }
    }

    @Test
    public void testAcceptUserOffer() {

    }

    @Test
    public void testApproveCourierOffer() {
    }

    @Test
    public void testDeleteCourierOffer() {
    }

    @Test
    public void testFinishCourierOffer() {
    }

    @Test
    public void testSendOfferToCourier() {
    }

    @Test
    public void testFindApprovedCourierOffers() {
    }

    @Test
    public void testFindCourierOffers() {
    }
}