package com.leandroyabut.utopiaairlines.application.entity.flight;

import com.leandroyabut.utopiaairlines.application.entity.airplane.Airplane;

import java.time.LocalDateTime;

public class Flight {
    private int id;
    private Route route;
    private Airplane airplane;
    private LocalDateTime departureTime;
    private int reservedSeats;
    private float seatPrice;

    public Flight(int id, Route route, Airplane airplane, LocalDateTime departureTime, int reservedSeats, float seatPrice) {
        this.id = id;
        this.route = route;
        this.airplane = airplane;
        this.departureTime = departureTime;
        this.reservedSeats = reservedSeats;
        this.seatPrice = seatPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public int getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(int reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

    public float getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(float seatPrice) {
        this.seatPrice = seatPrice;
    }
}
