package com.leandroyabut.utopiaairlines.application.ui.menus.administrator;

import com.leandroyabut.utopiaairlines.application.entity.airplane.Airplane;
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
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FlightCRUDMenu extends Menu {

    private List<Flight> flights;

    public FlightCRUDMenu(Menu invokingMenu) {
        super(invokingMenu);
        setTitle("Flights");
        setOptions("Add", "Update", "Delete", "Save", "Cancel...");
        setPrompt("Selection: ", 1, 5);
    }

    @Override
    public void start() {

        try {

            flights = DAOHandler.getInstance().getFlightDAO().getFlights();
            StringBuilder builder = new StringBuilder();

            flights.forEach(flight -> {
                String flightDetails = getHelper().getFlightDetailsString(flight);
                builder.append("Flight #");
                builder.append(flight.getId());
                builder.append("\n-------------------------------------------\n");
                builder.append(flightDetails);
                builder.append("\n\n\n");
            });

            setMessage(builder.toString());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        int selection = printMenu();

        switch (selection) {

            case 1:
                Flight flight = new Flight();
                try {
                    addFlight(flight);
                } catch (SQLException throwables) {
                    System.out.println("Unable to add flight...");
                    throwables.printStackTrace();
                }
                break;

            case 2:
                int flightId = getHelper().promptForInt("Enter Flight # to update: ", 1, Integer.MAX_VALUE);
                updateFlight(flightId);
                break;

            case 3:
                int deleteFlightId = getHelper().promptForInt("Enter flight # to delete: ", 1, Integer.MAX_VALUE);
                deleteFlight(deleteFlightId);
                break;

            case 4:
                try {
                    DAOHandler.getInstance().commit();
                } catch (SQLException throwables) {
                    DAOHandler.getInstance().rollback();
                    System.out.println("Unable to save these changes...");
                }
                break;

            case 5:
                DAOHandler.getInstance().rollback();
                back();
                break;

        }

        start();

    }

    private void addFlight(Flight flight) throws SQLException {

        int id = getHelper().promptForInt("Enter Flight ID: ", 0, Integer.MAX_VALUE);
        Route route = pickRoute();
        int airplaneId = getHelper().promptForInt("Enter Airplane ID: ", 1, Integer.MAX_VALUE);
        Airplane airplane = DAOHandler.getInstance().getFlightDAO().getAirplane(airplaneId);
        LocalDate departDate = Date.valueOf(getHelper().promptForString("Enter departure date (YYYY-MM-DD): ")).toLocalDate();
        LocalTime departTime = Time.valueOf(getHelper().promptForString("Enter departure time (hh:mm:ss): ")).toLocalTime();
        LocalDate arriveDate = Date.valueOf(getHelper().promptForString("Enter arrive date (YYYY-MM-DD): ")).toLocalDate();
        LocalTime arriveTime = Time.valueOf(getHelper().promptForString("Enter arrive time (hh:mm:ss): ")).toLocalTime();
        int reservedEconomy = getHelper().promptForInt("Currently reserved economy seats: ", 1, Integer.MAX_VALUE);
        int reservedFirst = getHelper().promptForInt("Currently reserved first seats: ", 1, Integer.MAX_VALUE);
        int reserveBusiness = getHelper().promptForInt("Currently reserved business seats: ", 1, Integer.MAX_VALUE);
        float economyPrice = getHelper().promptForFloat("Enter price of economy seats: $", 0, Float.MAX_VALUE);
        float firstPrice = getHelper().promptForFloat("Enter price of first seats: $", 0, Float.MAX_VALUE);
        float businessPrice = getHelper().promptForFloat("Enter price of business seats: $", 0, Float.MAX_VALUE);

        flight.setId(id);
        flight.setRoute(route);
        flight.setAirplane(airplane);
        flight.setDepartureTime(LocalDateTime.of(departDate, departTime));
        flight.setArrivalTime(LocalDateTime.of(arriveDate, arriveTime));
        flight.setEconomyReservedSeats(reservedEconomy);
        flight.setBusinessReservedSeats(reserveBusiness);
        flight.setFirstReservedSeats(reservedFirst);
        flight.setEconomySeatPrice(economyPrice);
        flight.setFirstSeatPrice(firstPrice);
        flight.setBusinessSeatPrice(businessPrice);

        DAOHandler.getInstance().getFlightDAO().addFlight(flight);

    }

    private void updateFlight(int id) {

        try {
            if(DAOHandler.getInstance().getFlightDAO().flightExist(id))
                DAOHandler.getInstance().getFlightDAO().updateFlight(id, getHelper().promptForString("Enter column to edit: "), getHelper().promptForString("Enter new value: "));
            else System.out.println("Can't update a flight that doesn't exist...");
        } catch (SQLException throwables) {
            System.out.println("Unable to update flight...");
        }

    }

    private void deleteFlight(int id) {
        try {
            if(DAOHandler.getInstance().getFlightDAO().flightExist(id))
                DAOHandler.getInstance().getFlightDAO().deleteFlight(id);
            else System.out.println("Can't delete a flight that doesn't exist...");
        } catch (SQLException throwables) {
            System.out.println("Unable to delete flight...");
        }
    }

    private Route pickRoute() throws SQLException {

        FlightDAO flightDAO = DAOHandler.getInstance().getFlightDAO();
        List<Route> routes = flightDAO.getRoutes();

        AtomicInteger i = new AtomicInteger(1);
        routes.forEach(route -> {

            System.out.println(i + ") " + getRouteString(route));
            i.getAndIncrement();

        });

        System.out.println(i + ") Create new...");

        int selection = getHelper().promptForInt("Enter route to use: ", 1, routes.size() + 1);

        if (selection <= routes.size()) {
            return routes.get(selection - 1);
        } else {
            Airport originAirport = createAirport(flightDAO);

            Airport destinationAirport = createAirport(flightDAO);

            Route route = new Route(originAirport, destinationAirport);
            if(!flightDAO.routeExists(flightDAO.getRouteIdByAirports(originAirport.getAirportCode(), destinationAirport.getAirportCode())))
                flightDAO.addRoute(route);

            return flightDAO.getRouteByAirportIds(originAirport.getAirportCode(), destinationAirport.getAirportCode());
        }
    }

    private Airport createAirport(FlightDAO flightDAO) throws SQLException {
        Airport airport;
        String code = getHelper().promptForString("Enter 3 letter code: ");
        if(!flightDAO.airportExists(code)) {
            String oCity = getHelper().promptForString("Enter city for " + code + ": ");
            airport = new Airport(code, oCity);
            flightDAO.addAirport(airport);
            flightDAO.commit();
        }
        airport = flightDAO.getAirportByCode(code);
        return airport;
    }
}
