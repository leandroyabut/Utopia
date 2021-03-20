package com.leandroyabut.utopiaairlines.application.entity.flight;

import java.util.Objects;

public class Route {
    private int id;
    private Airport originAirport;
    private Airport destinationAirport;

    public Route(int id, Airport originAirport, Airport destinationAirport) {
        this.id = id;
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Airport getOriginAirport() {
        return originAirport;
    }

    public void setOriginAirport(Airport originAirport) {
        this.originAirport = originAirport;
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return id == route.id && originAirport.equals(route.originAirport) && destinationAirport.equals(route.destinationAirport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originAirport, destinationAirport);
    }
}
