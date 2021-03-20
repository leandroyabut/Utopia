package com.leandroyabut.utopiaairlines.application.service.dao.flightbooking;

import com.leandroyabut.utopiaairlines.application.entity.FlightBooking;
import com.leandroyabut.utopiaairlines.application.service.dao.DataAccessObject;
import com.leandroyabut.utopiaairlines.application.service.handler.DAOHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FlightBookingDAO extends DataAccessObject {

    public FlightBookingDAO() throws SQLException {
    }

    public FlightBooking getFlightBookingByIds(int flightId, int bookingId) throws SQLException {
        FlightBooking flightBooking = null;

        ResultSet resultSet = query("select * from flight_bookings where flight_id = ? and booking_id = ?", flightId, bookingId);

        if(resultSet.next()) {
            flightBooking = new FlightBooking(DAOHandler.getInstance().getFlightDAO().getFlightById(resultSet.getInt("flight_id")), DAOHandler.getInstance().getBookingDAO().getBookingById(resultSet.getInt("booking_id")));
        }

        return flightBooking;
    }
}
