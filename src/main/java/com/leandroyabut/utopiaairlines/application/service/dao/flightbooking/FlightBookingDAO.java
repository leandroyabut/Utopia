package com.leandroyabut.utopiaairlines.application.service.dao.flightbooking;

import com.leandroyabut.utopiaairlines.application.entity.FlightBooking;
import com.leandroyabut.utopiaairlines.application.entity.user.User;
import com.leandroyabut.utopiaairlines.application.service.dao.DataAccessObject;
import com.leandroyabut.utopiaairlines.application.service.handler.DAOHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightBookingDAO extends DataAccessObject {

    public FlightBookingDAO() throws SQLException {
    }

    public FlightBooking getFlightBookingByIds(int flightId, int bookingId) throws SQLException {
        FlightBooking flightBooking = null;

        ResultSet resultSet = query("select * from flight_bookings where flight_id = ? and booking_id = ?", flightId, bookingId);

        if(resultSet.next())
            flightBooking = new FlightBooking(DAOHandler.getInstance().getFlightDAO().getFlightById(resultSet.getInt("flight_id")), DAOHandler.getInstance().getBookingDAO().getBookingById(resultSet.getInt("booking_id")));

        return flightBooking;
    }

    public List<FlightBooking> getFlightBookings(User user) throws SQLException {

        List<FlightBooking> flightBookings = new ArrayList<>();

        ResultSet resultSet = query("select flight_bookings.flight_id, booking.id as 'booking_id', booking_user.user_id from flight_bookings inner join booking on flight_bookings.booking_id = booking.id inner join booking_user on booking_user.booking_id = booking.id where booking_user.user_id = ?", user.getId());

        while(resultSet.next()) {

            flightBookings.add(getFlightBookingByIds(resultSet.getInt("flight_id"), resultSet.getInt("booking_id")));

        }

        return flightBookings;

    }

    public void addFlightBooking(int flightId, int bookingId) throws SQLException {
        if(!flightBookingExists(flightId, bookingId))
            update("insert into flight_bookings values (?, ?)", flightId, bookingId);
    }

    public void deleteFlightBooking(int flightId, int bookingId) throws SQLException {
        if(flightBookingExists(flightId, bookingId))
            update("delete from flight_bookings where flight_id = ? and booking_id = ?", flightId, bookingId);
    }

    public boolean flightBookingExists(int flightId, int bookingId) throws SQLException {
        return query("select * from flight_bookings where flight_id = ? and booking_id = ?", flightId, bookingId).next();
    }

    public void deleteFBOnFlightId(int id) throws SQLException {
        ResultSet rs = query("select * from flight_bookings where flight_id = ?", id);
        while(rs.next()) {
            DAOHandler.getInstance().getBookingDAO().deleteBooking(rs.getInt("booking_id"));
            update("delete from flight_bookings where flight_id = ?", id);
        }
    }
}
