package com.leandroyabut.utopiaairlines.application.entity.user;

import com.leandroyabut.utopiaairlines.application.entity.booking.Booking;

public class BookingGuest {
    private Booking booking;
    private String contactEmail;
    private String contactPhone;

    public BookingGuest(String contactEmail, String contactPhone) {
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
