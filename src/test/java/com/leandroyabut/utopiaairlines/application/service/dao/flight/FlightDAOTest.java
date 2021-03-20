package com.leandroyabut.utopiaairlines.application.service.dao.flight;

import com.leandroyabut.utopiaairlines.application.entity.airplane.Airplane;
import com.leandroyabut.utopiaairlines.application.entity.airplane.AirplaneType;
import junit.framework.TestCase;

import java.sql.SQLException;

public class FlightDAOTest extends TestCase {

    private final FlightDAO flightDAO = new FlightDAO();

    public FlightDAOTest() throws SQLException {
    }

    public void testGetAirplaneType() throws SQLException {
        AirplaneType type = flightDAO.getAirplaneType(3);
        int expectedMaxCapacity = 200;
        assertEquals(expectedMaxCapacity, type.getMaxCapacity());
    }

    public void testGetAirplane() throws SQLException {
        Airplane airplane = flightDAO.getAirplane(1);
        int expectedTypeId = 2;
        int expectedMaxCapacity = 150;
        assertEquals(expectedTypeId, airplane.getType().getId());
        assertEquals(expectedMaxCapacity, airplane.getType().getMaxCapacity());
    }
}