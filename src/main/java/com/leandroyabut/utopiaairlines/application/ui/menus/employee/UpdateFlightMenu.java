package com.leandroyabut.utopiaairlines.application.ui.menus.employee;

import com.leandroyabut.utopiaairlines.application.entity.flight.Airport;
import com.leandroyabut.utopiaairlines.application.entity.flight.Flight;
import com.leandroyabut.utopiaairlines.application.entity.flight.Route;
import com.leandroyabut.utopiaairlines.application.service.dao.flight.FlightDAO;
import com.leandroyabut.utopiaairlines.application.service.handler.DAOHandler;
import com.leandroyabut.utopiaairlines.application.ui.menus.Menu;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class UpdateFlightMenu extends Menu {

    private Flight flight;
    private FlightDAO flightDAO;

    public UpdateFlightMenu(Menu invokingMenu, Flight flight) {
        super(invokingMenu);
        this.flight = flight;
        flightDAO = DAOHandler.getInstance().getFlightDAO();
    }

    @Override
    public void start() {

        System.out.println("Update Flight");
        System.out.println("-----------------------------");
        System.out.println("Enter 'quit' at any point to cancel this operation...\n");

        final String[] origin = new String[2];
        final String[] destination = new String[2];
        final LocalDate[] departDate = new LocalDate[1];
        final LocalDate[] arriveDate = new LocalDate[1];
        final LocalTime[] departTime = new LocalTime[1];
        final LocalTime[] arriveTime = new LocalTime[1];

        // Change Origin
        promptForTextInput("Enter new origin airport code and city. (ex. DEN, Denver). Enter 'na' for no change...", command -> {
            if(!command.equalsIgnoreCase("na")) {
                try {
                    origin[0] = command.split(",")[0].trim().toUpperCase().substring(0, 3);
                    origin[1] = command.split(",")[1].trim();
                } catch(IndexOutOfBoundsException e) {
                    System.out.println("Input is invalid...");
                }
            }
        }, this::quit);

        // Change Destination
        promptForTextInput("Enter new destination airport code and city. (ex. DEN, Denver). Enter 'na' for no change...", command -> {
            if(!command.equalsIgnoreCase("na")) {
                try {
                    destination[0] = command.split(",")[0].trim().toUpperCase().substring(0, 3);
                    destination[1] = command.split(",")[1].trim();
                } catch(IndexOutOfBoundsException e) {
                    System.out.println("Input is invalid...");
                }
            }
        }, this::quit);

        // Change Departure Date
        promptForTextInput("Enter new departure date. (YYYY-MM-DD). Enter 'na' for no change...", command -> {
            if(!command.equalsIgnoreCase("na")) {
                try {
                    departDate[0] = Date.valueOf(command).toLocalDate();
                } catch (IllegalArgumentException e) {
                    System.out.println("Format was not YYYY-MM-DD...");
                }
            }
        }, this::quit);

        // Change Departure Time
        promptForTextInput("Enter new departure time. (hh:mm:ss). Enter 'na' for no change...", command -> {
            if(!command.equalsIgnoreCase("na")) {
                try {
                    departTime[0] = Time.valueOf(command).toLocalTime();
                } catch (IllegalArgumentException e) {
                    System.out.println("Format was not hh:mm:ss...");
                }
            }
        }, this::quit);

        // Change Arrival Date
        promptForTextInput("Enter new arrival date. (YYYY-MM-DD). Enter 'na' for no change...", command -> {
            if(!command.equalsIgnoreCase("na")) {
                try {
                    arriveDate[0] = Date.valueOf(command).toLocalDate();
                } catch (IllegalArgumentException e) {
                    System.out.println("Format was not YYYY-MM-DD...");
                }
            }
        }, this::quit);

        // Change Arrival Time
        promptForTextInput("Enter new arrival time. (hh:mm:ss). Enter 'na' for no change...", command -> {
            if(!command.equalsIgnoreCase("na")) {
                try {
                    arriveTime[0] = Time.valueOf(command).toLocalTime();
                } catch (IllegalArgumentException e) {
                    System.out.println("Format was not hh:mm:ss...");
                }
            }
        }, this::quit);

        // Ask to save
        promptForTextInput("Would you like to save these changes? (Y/N)", command -> {

            if(command.equalsIgnoreCase("Y")) {
                try {
                    save(origin, destination, departDate, departTime, arriveDate, arriveTime);
                } catch (SQLException throwables) {
                    try {
                        flightDAO.rollback();
                    } catch (SQLException e) {
                        System.out.println("Unable to rollback changes...");
                    }
                    System.out.println("Unable to save. Please contact your administrator.");
                    throwables.printStackTrace();
                }
            }

        }, this::quit);

        System.out.println("\n--------------------------------------------\n\n");

        back();

    }

    private void quit() {
        try {
            flightDAO.rollback();
            System.out.println("\n--------------------------------------------\n\n");
        } catch (SQLException throwables) {
            System.out.println("Unable to rollback to previous state...");
        }
        this.back();
    }

    private void save(String[] origin, String[] destination, LocalDate[] departDate, LocalTime[] departTime, LocalDate[] arriveDate, LocalTime[] arriveTime) throws SQLException {
        FlightDAO flightDAO = DAOHandler.getInstance().getFlightDAO();

        Airport originAirport = flight.getRoute().getOriginAirport();
        Airport destinationAirport = flight.getRoute().getDestinationAirport();
        LocalDate newDepartDate = flight.getDepartureTime().toLocalDate();
        LocalTime newDepartTime = flight.getDepartureTime().toLocalTime();
        LocalDate newArriveDate = flight.getArrivalTime().toLocalDate();
        LocalTime newArriveTime = flight.getArrivalTime().toLocalTime();

        Route newRoute;

        // Check airports
        if(origin[0] != null) {
            if (!flightDAO.airportExists(origin[0])) {
                flightDAO.addAirport(new Airport(origin[0], origin[1]));
            }
            originAirport = flightDAO.getAirportByCode(origin[0]);
        }

        if(destination[0] != null) {
            if (!flightDAO.airportExists(destination[0])) {
                flightDAO.addAirport(new Airport(destination[0], destination[1]));
            }
            destinationAirport = flightDAO.getAirportByCode(destination[0]);
        }

        // Check routes
        if(!flightDAO.routeExists(flightDAO.getRouteIdByAirports(originAirport.getAirportCode(), destinationAirport.getAirportCode()))) {
            flightDAO.addRoute(new Route(originAirport, destinationAirport));
        }
        newRoute = flightDAO.getRouteByAirportIds(originAirport.getAirportCode(), destinationAirport.getAirportCode());

        // Check times
        if(departDate[0] != null) {
            newDepartDate = departDate[0];
        }

        if(departTime[0] != null) {
            newDepartTime = departTime[0];
        }

        if(arriveDate[0] != null) {
            newArriveDate = arriveDate[0];
        }

        if(arriveTime[0] != null) {
            newArriveTime = arriveTime[0];
        }

        LocalDateTime newDepartDateTime = LocalDateTime.of(newDepartDate, newDepartTime);
        LocalDateTime newArriveDateTime = LocalDateTime.of(newArriveDate, newArriveTime);

        flightDAO.updateFlight(flight.getId(), "route_id", newRoute.getId());
        flightDAO.updateFlight(flight.getId(), "departure_time", newDepartDateTime);
        flightDAO.updateFlight(flight.getId(), "arrival_time", newArriveDateTime);

        flightDAO.commit();
    }

}
