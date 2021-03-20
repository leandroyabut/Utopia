package com.leandroyabut.utopiaairlines.application.entity.flight;

import java.util.Objects;

public class Airport {
    private String airportCode;
    private String city;

    public Airport(String airportCode, String city) {
        this.airportCode = airportCode;
        this.city = city;
    }


    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return airportCode.equals(airport.airportCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airportCode);
    }
}
