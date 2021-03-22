package com.leandroyabut.utopiaairlines.application.service.dao.booking;

import com.leandroyabut.utopiaairlines.application.entity.booking.Booking;
import com.leandroyabut.utopiaairlines.application.entity.booking.Passenger;
import com.leandroyabut.utopiaairlines.application.entity.booking.Payment;
import com.leandroyabut.utopiaairlines.application.entity.flight.Flight;
import com.leandroyabut.utopiaairlines.application.entity.user.BookingGuest;
import com.leandroyabut.utopiaairlines.application.entity.user.User;
import com.leandroyabut.utopiaairlines.application.service.dao.DataAccessObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO extends DataAccessObject {
    public BookingDAO() throws SQLException {
    }

    public Payment getPaymentByBooking(Booking booking) throws SQLException {
        Payment payment = null;

        ResultSet resultSet = query("select * from booking_payment where booking_id = ?", booking.getId());

        if(resultSet.next()) {
            payment = new Payment(resultSet.getString("stripe_id"), resultSet.getBoolean("refunded"));
        }

        return payment;
    }

    public Passenger getPassengerById(int id) throws SQLException {
        Passenger passenger = null;

        ResultSet resultSet = query("select * from passenger where id = ?", id);

        if(resultSet.next()) {
            passenger = new Passenger(
                    resultSet.getInt("id"),
                    resultSet.getString("given_name"),
                    resultSet.getString("family_name"),
                    resultSet.getDate("dob").toLocalDate(),
                    resultSet.getString("gender"),
                    resultSet.getString("address")
            );
        }

        return passenger;
    }

    public List<Passenger> getPassengersByBooking(Booking booking) throws SQLException {
        List<Passenger> passengers = new ArrayList<>();

        ResultSet resultSet = query("select id from passenger where booking_id = ?", booking.getId());

        while(resultSet.next()) {
            passengers.add(getPassengerById(resultSet.getInt("id")));
        }

        return passengers;
    }

    public Booking getBookingById(int id) throws SQLException {
        Booking booking = null;

        ResultSet resultSet = query("select * from booking where id = ?", id);

        if(resultSet.next()) {
            booking = new Booking(resultSet.getInt("id"), resultSet.getBoolean("is_active"), resultSet.getString("confirmation_code"));
            booking.setPassengers(getPassengersByBooking(booking));
        }

        return booking;
    }

    public Booking getBookingByConfirmationCode(String confirmationCode) throws SQLException {
        Booking booking = null;

        ResultSet resultSet = query("select * from booking where confirmation_code = ?", confirmationCode);

        if(resultSet.next()) {
            booking = new Booking(resultSet.getInt("id"), resultSet.getBoolean("is_active"), resultSet.getString("confirmation_code"));
            booking.setPassengers(getPassengersByBooking(booking));
        }

        return booking;
    }

    public BookingGuest getBookingGuestByBooking(int bookingId) throws SQLException {
        BookingGuest guest = null;

        ResultSet resultSet = query("select * from booking_guest where booking_id = ?", bookingId);

        if(resultSet.next()) {
            guest = new BookingGuest(resultSet.getString("contact_email"), resultSet.getString("get_phone"));
            guest.setBooking(getBookingById(bookingId));
        }

        return guest;
    }

    public void addFlightBooking(Flight flight, Booking booking) throws SQLException {
        if(!flightBookingExists(flight.getId(), booking.getId()))
            update("insert into flight_bookings values(?, ?)", flight.getId(), booking.getId());
    }

    public void deleteFlightBooking(int flightId, int bookingId) throws SQLException {
        if(flightBookingExists(flightId, bookingId))
            update("delete from flight_bookings where flight_id = ? and booking_id = ?", flightId, bookingId);
    }

    public void addBooking(Booking booking) throws SQLException {
        if(!bookingExists(booking.getId()))
            update("insert into booking values(?, ?, ?)", booking.getId()>0?booking.getId():null, booking.isActive(), booking.getConfirmationCode());
    }

    public void addPayment(int bookingId, Payment payment) throws SQLException {
        if(!paymentExists(bookingId))
            update("insert into booking_payment values(?, ?, ?)", bookingId, payment.getStripeId(), payment.isRefunded());
    }

    public void addPassenger(int bookingId, Passenger passenger) throws SQLException {
        if(!passengerExists(passenger.getId()))
            update("insert into passenger values(?, ?, ?, ?, ?, ?, ?, ?)",
                    passenger.getId(),
                    bookingId,
                    passenger.getGivenName(),
                    passenger.getFamilyName(),
                    passenger.getDateOfBirth(),
                    passenger.getGender(),
                    passenger.getAddress());
    }

    public void addBookingUser(Booking booking, User user) throws SQLException {
        if(!bookingUserExists(booking.getId(), user.getId()))
            update("insert into booking_user values(?, ?)", booking.getId(), user.getId());
    }

    public void deleteBookingUser(int bookingId, int userId) throws SQLException {
        if(bookingUserExists(bookingId, userId))
            update("delete from booking_user where booking_id = ? and user_id = ?", bookingId, userId);
    }

    public void updateBookingUser(int bookingId, int userId, String columnLabel, Object value) throws SQLException {
        if(bookingUserExists(bookingId, userId))
            update("update booking_user set " + whiteListedColumnName(columnLabel) + " = ? where booking_id = ? and user_id = ?", value, bookingId, userId);
    }

    public void addBookingGuest(int bookingId, BookingGuest guest) throws SQLException {
        if(!bookingGuestExists(bookingId))
            update("insert into booking_guest values(?, ?, ?)", bookingId, guest.getContactEmail(), guest.getContactPhone());
    }

    public void deleteBooking(int bookingId) throws SQLException {
        if(bookingExists(bookingId)) {
            update("delete from booking_user where booking_id = ?", bookingId);
            update("delete from booking where id = ?", bookingId);
        }
    }

    public void deletePayment(int bookingId) throws SQLException {
        if(paymentExists(bookingId))
            update("delete from booking_payment where booking_id = ?", bookingId);
    }

    public void deletePassenger(int id) throws SQLException {
        if(passengerExists(id))
            update("delete from passenger where id = ?", id);
    }

    public void deleteBookingGuest(int bookingId) throws SQLException {
        if(bookingGuestExists(bookingId))
            update("delete from booking_guest where booking_id = ?", bookingId);
    }

    public void updateBooking(int id, String columnLabel, Object value) throws SQLException {
        if(bookingExists(id))
            update("update booking set " + whiteListedColumnName(columnLabel) + " = ? where id = ?", value, id);
    }

    public void updatePayment(int bookingId, String columnLabel, Object value) throws SQLException {
        if(passengerExists(bookingId))
            update("update booking_payment set " + whiteListedColumnName(columnLabel) + " = ? where booking_id = ?", value, bookingId);
    }

    public void updateBookingGuest(int bookingId, String columnLabel, Object value) throws SQLException {
        if(bookingGuestExists(bookingId))
            update("update booking_guest set " + whiteListedColumnName(columnLabel) + " = ? where booking_id = ?", value, bookingId);
    }

    public boolean bookingExists(int id) throws SQLException {
        return query("select id from booking where id = ?", id).next();
    }

    public boolean passengerExists(int id) throws SQLException {
        return query("select id from passenger where id = ?", id).next();
    }

    public boolean paymentExists(int bookingId) throws SQLException {
        return query("select booking_id from booking_payment where booking_id = ?", bookingId).next();
    }

    public boolean bookingGuestExists(int bookingId) throws SQLException {
        return query("select booking_id from booking_guest where booking_id = ?", bookingId).next();
    }

    public boolean bookingUserExists(int bookingId, int userId) throws SQLException {
        return query("select * from booking_user where booking_id = ? and user_id = ?", bookingId, userId).next();
    }

    public boolean flightBookingExists(int flightId, int bookingId) throws SQLException {
        return query("select * from flight_bookings where flight_id = ? and booking_id = ?", flightId, bookingId).next();
    }
}