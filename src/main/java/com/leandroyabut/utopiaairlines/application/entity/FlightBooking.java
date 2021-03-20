package com.leandroyabut.utopiaairlines.application.entity;

import com.leandroyabut.utopiaairlines.application.entity.booking.Booking;
import com.leandroyabut.utopiaairlines.application.entity.flight.Flight;

public class FlightBooking {
    private Flight flight;
    private Booking booking;

    public FlightBooking(Flight flight, Booking booking) {
        this.flight = flight;
        this.booking = booking;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
