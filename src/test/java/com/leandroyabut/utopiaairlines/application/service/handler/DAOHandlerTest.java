package com.leandroyabut.utopiaairlines.application.service.handler;

import com.leandroyabut.utopiaairlines.application.service.dao.booking.BookingDAO;
import com.leandroyabut.utopiaairlines.application.service.dao.flight.FlightDAO;
import com.leandroyabut.utopiaairlines.application.service.dao.flightbooking.FlightBookingDAO;
import com.leandroyabut.utopiaairlines.application.service.dao.user.UserDAO;
import junit.framework.TestCase;

public class DAOHandlerTest extends TestCase {

    public void testGetInstance() {
        DAOHandler handler = DAOHandler.getInstance();
        DAOHandler handler2 = DAOHandler.getInstance();

        assertEquals(handler.hashCode(), handler2.hashCode());
    }

    public void testGetFlightDAO() {
        FlightDAO flightDAO = DAOHandler.getInstance().getFlightDAO();
        assertNotNull(flightDAO);
    }

    public void testGetBookingDAO() {
        BookingDAO bookingDAO = DAOHandler.getInstance().getBookingDAO();
        assertNotNull(bookingDAO);
    }

    public void testGetFlightBookingDAO() {
        FlightBookingDAO flightBookingDAO = DAOHandler.getInstance().getFlightBookingDAO();
        assertNotNull(flightBookingDAO);
    }

    public void testGetUserDAO() {
        UserDAO userDAO = DAOHandler.getInstance().getUserDAO();
        assertNotNull(userDAO);
    }
}