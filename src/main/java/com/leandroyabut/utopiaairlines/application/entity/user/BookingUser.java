package com.leandroyabut.utopiaairlines.application.entity.user;

import com.leandroyabut.utopiaairlines.application.entity.booking.Booking;

public class BookingUser extends User {

    private Booking booking;

    public BookingUser(int id, UserRole role, String givenName, String familyName, String username, String email, String password, String phone) {
        super(id, role, givenName, familyName, username, email, password, phone);
    }

    public BookingUser(User user, Booking booking) {
        super(user.getId(), user.getRole(), user.getGivenName(), user.getFamilyName(), user.getUsername(), user.getEmail(), user.getPassword(), user.getPhone());
        setBooking(booking);
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Booking getBooking() {
        return booking;
    }
}
