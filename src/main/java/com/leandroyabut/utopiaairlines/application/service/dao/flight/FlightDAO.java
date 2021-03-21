package com.leandroyabut.utopiaairlines.application.service.dao.flight;

import com.leandroyabut.utopiaairlines.application.entity.airplane.Airplane;
import com.leandroyabut.utopiaairlines.application.entity.airplane.AirplaneType;
import com.leandroyabut.utopiaairlines.application.entity.flight.Airport;
import com.leandroyabut.utopiaairlines.application.entity.flight.Flight;
import com.leandroyabut.utopiaairlines.application.entity.flight.Route;
import com.leandroyabut.utopiaairlines.application.service.dao.DataAccessObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO extends DataAccessObject {

    public FlightDAO() throws SQLException {
    }

    public Airport getAirportByCode(String code) throws SQLException {
        Airport airport = null;

        ResultSet resultSet = query("select * from airport where iata_id = ?", code);

        if(resultSet.next()) {
            airport = new Airport(resultSet.getString("iata_id"), resultSet.getString("city"));
        }

        return airport;
    }

    public Route getRouteById(int id) throws SQLException {
        Route route = null;

        ResultSet resultSet = query("select * from route where id = ?", id);

        if(resultSet.next()) {
            route = new Route(resultSet.getInt("id"), getAirportByCode(resultSet.getString("origin_id")), getAirportByCode(resultSet.getString("destination_id")));
        }

        return route;
    }

    public Route getRouteByAirportIds(String originId, String destinationId) throws SQLException {
        return getRouteById(getRouteIdByAirports(originId, destinationId));
    }

    public AirplaneType getAirplaneType(int id) throws SQLException {
        AirplaneType type = null;

        ResultSet resultSet = query("select * from airplane_type where id = ?", id);

        if(resultSet.next()) {
            type = new AirplaneType(resultSet.getInt("id"), resultSet.getInt("max_capacity"), resultSet.getInt("max_first_capacity"), resultSet.getInt("max_business_capacity"));
        }

        return type;
    }

    public Airplane getAirplane(int id) throws SQLException {
        Airplane airplane = null;

        ResultSet resultSet = query("select * from airplane where id = ?", id);

        if(resultSet.next()) {
            airplane = new Airplane(resultSet.getInt("id"), getAirplaneType(resultSet.getInt("type_id")));
        }

        return airplane;
    }

    public Flight getFlightById(int id) throws SQLException {
        Flight flight = null;

        ResultSet resultSet = query("select * from flight where id = ?", id);

        if(resultSet.next()) {
            flight = new Flight(
                    resultSet.getInt("id"),
                    getRouteById(resultSet.getInt("route_id")),
                    getAirplane(resultSet.getInt("airplane_id")),
                    LocalDateTime.of(resultSet.getDate("arrival_time").toLocalDate(), resultSet.getTime("arrival_time").toLocalTime()),
                    LocalDateTime.of(resultSet.getDate("departure_time").toLocalDate(), resultSet.getTime("departure_time").toLocalTime()),
                    resultSet.getInt("reserved_economy_seats"),
                    resultSet.getInt("reserved_business_seats"),
                    resultSet.getInt("reserved_first_seats"),
                    resultSet.getFloat("economy_seat_price"),
                    resultSet.getFloat("business_seat_price"),
                    resultSet.getFloat("first_seat_price")
            );
        }

        return flight;
    }

    public List<Flight> getFlights() throws SQLException {
        List<Flight> flights = new ArrayList<>();

        ResultSet resultSet = query("select * from flight");

        while(resultSet.next()) {
            flights.add(getFlightById(resultSet.getInt("id")));
        }

        return flights;
    }

    public void addAirport(Airport airport) throws SQLException {
        if(!airportExists(airport.getAirportCode()))
            update("insert into airport values(?, ?)", airport.getAirportCode(), airport.getCity());
    }

    public void deleteAirport(String code) throws SQLException {
        if(airportExists(code))
            update("delete from airport where iata_id = ?", code);
    }

    public void updateAirport(String code, String columnLabel, Object value) throws SQLException {
        update("update airport set " + whiteListedColumnName(columnLabel) + " = ? where iata_id = ?", value, code);
    }

    public void addRoute(Route route) throws SQLException {
        if(!routeExists(route.getId()))
            update("insert into route values(?, ?, ?)", route.getId()>0?route.getId():null, route.getOriginAirport().getAirportCode(), route.getDestinationAirport().getAirportCode());
    }

    public void deleteRoute(int id) throws SQLException {
        if(routeExists(id))
            update("delete from route where id = ?", id);
    }

    public void updateRoute(int id, String columnLabel, Object value) throws SQLException {
        if(routeExists(id))
            update("update route set " + whiteListedColumnName(columnLabel) + " = ? where id = ?", value, id);
    }

    public int getRouteIdByAirports(String originCode, String destCode) throws SQLException {
        int id = 0;
        ResultSet resultSet = query("select id from route where origin_id = ? and destination_id = ?", originCode, destCode);
        if(resultSet.next()) id = resultSet.getInt("id");
        return id;
    }

    public void addAirplaneType(AirplaneType type) throws SQLException {
        if(!airplaneTypeExists(type.getId()))
            update("insert into airplane_type values(?, ?, ?, ?)", type.getId()>0?type.getId():null, type.getMaxCapacity(), type.getMaxFirstCapacity(), type.getMaxBusinessCapacity());
    }

    public void deleteAirplaneType(int id) throws SQLException {
        if(airplaneTypeExists(id))
            update("delete from airplane_type where id = ?", id);
    }

    public void updateAirplaneType(int id, String columnLabel, Object value) throws SQLException {
        if(airplaneTypeExists(id))
            update("update airplane_type set " + whiteListedColumnName(columnLabel) + " = ? where id = ?", value, id);
    }

    public void addAirplane(Airplane airplane) throws SQLException {
        if(airplaneExists(airplane.getId()))
            update("insert into airplane values(?, ?)", airplane.getId()>0?airplane.getId():null, airplane.getType().getId());
    }

    public void deleteAirplane(int id) throws SQLException {
        if(airplaneExists(id))
            update("delete from airplane where id = ?", id);
    }

    public void updateAirplane(int id, String columnName, Object value) throws SQLException {
        if(airplaneExists(id))
            update("update airplane set " + whiteListedColumnName(columnName) + " = ? where id = ?", value, id);
    }

    public void addFlight(Flight flight) throws SQLException {
        if(!flightExist(flight.getId()))
            update("insert into flight values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    flight.getId(),
                    flight.getRoute().getId(),
                    flight.getAirplane().getId(),
                    flight.getArrivalTime().toString(),
                    flight.getDepartureTime().toString(),
                    flight.getEconomyReservedSeats(),
                    flight.getBusinessReservedSeats(),
                    flight.getFirstReservedSeats(),
                    flight.getEconomySeatPrice(),
                    flight.getBusinessSeatPrice(),
                    flight.getFirstSeatPrice());
    }

    public void deleteFlight(int id) throws SQLException {
        if(flightExist(id))
            update("delete from flight where id = ?", id);
    }

    public void updateFlight(int id, String columnLabel, Object value) throws SQLException {
        if(flightExist(id))
            update("update flight set " + whiteListedColumnName(columnLabel) + " = ? where id = ?", value, id);
    }

    public boolean airportExists(String code) throws SQLException {
        return query("select iata_id from airport where iata_id = ?", code).next();
    }

    public boolean routeExists(int id) throws SQLException {
        return query("select id from route where id = ?", id).next();
    }

    public boolean airplaneTypeExists(int id) throws SQLException {
        return query("select id from airplane_type where id = ?", id).next();
    }

    public boolean airplaneExists(int id) throws SQLException {
        return query("select id from airplane where id = ?", id).next();
    }

    public boolean flightExist(int id) throws SQLException {
        return query("select id from flight where id = ?", id).next();
    }
}
