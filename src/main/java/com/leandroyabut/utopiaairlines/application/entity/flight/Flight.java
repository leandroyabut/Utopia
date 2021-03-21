package com.leandroyabut.utopiaairlines.application.entity.flight;

import com.leandroyabut.utopiaairlines.application.entity.airplane.Airplane;

import java.time.LocalDateTime;

public class Flight {

    private int id;
    private Route route;
    private Airplane airplane;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private int economyReservedSeats;
    private int businessReservedSeats;
    private int firstReservedSeats;
    private float economySeatPrice;
    private float businessSeatPrice;
    private float firstSeatPrice;

    public Flight(
            int id,
            Route route,
            Airplane airplane,
            LocalDateTime arrivalTime,
            LocalDateTime departureTime,
            int economyReservedSeats,
            int businessReservedSeats,
            int firstReservedSeats,
            float economySeatPrice,
            float businessSeatPrice,
            float firstSeatPrice) {
        this.id = id;
        this.route = route;
        this.airplane = airplane;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.economyReservedSeats = economyReservedSeats;
        this.businessReservedSeats = businessReservedSeats;
        this.firstReservedSeats = firstReservedSeats;
        this.economySeatPrice = economySeatPrice;
        this.businessSeatPrice = businessSeatPrice;
        this.firstSeatPrice = firstSeatPrice;
    }

    public Flight(){}

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

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getEconomyReservedSeats() {
        return economyReservedSeats;
    }

    public void setEconomyReservedSeats(int economyReservedSeats) {
        this.economyReservedSeats = economyReservedSeats;
    }

    public int getBusinessReservedSeats() {
        return businessReservedSeats;
    }

    public void setBusinessReservedSeats(int businessReservedSeats) {
        this.businessReservedSeats = businessReservedSeats;
    }

    public int getFirstReservedSeats() {
        return firstReservedSeats;
    }

    public void setFirstReservedSeats(int firstReservedSeats) {
        this.firstReservedSeats = firstReservedSeats;
    }

    public float getEconomySeatPrice() {
        return economySeatPrice;
    }

    public void setEconomySeatPrice(float economySeatPrice) {
        this.economySeatPrice = economySeatPrice;
    }

    public float getBusinessSeatPrice() {
        return businessSeatPrice;
    }

    public void setBusinessSeatPrice(float businessSeatPrice) {
        this.businessSeatPrice = businessSeatPrice;
    }

    public float getFirstSeatPrice() {
        return firstSeatPrice;
    }

    public void setFirstSeatPrice(float firstSeatPrice) {
        this.firstSeatPrice = firstSeatPrice;
    }
}
