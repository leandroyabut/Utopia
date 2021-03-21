package com.leandroyabut.utopiaairlines.application.ui.menus.employee;

import com.leandroyabut.utopiaairlines.application.entity.flight.Flight;
import com.leandroyabut.utopiaairlines.application.service.dao.flight.FlightDAO;
import com.leandroyabut.utopiaairlines.application.service.handler.DAOHandler;
import com.leandroyabut.utopiaairlines.application.ui.menus.Menu;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class FlightsMenu extends Menu {

    private FlightDAO flightDAO;
    private List<Flight> flights;

    public FlightsMenu(Menu invokingMenu) {
        super(invokingMenu);
        flightDAO = DAOHandler.getInstance().getFlightDAO();
        setTitle("Flights");
    }

    @Override
    public void start() {
        try {
            flights = flightDAO.getFlights();
        } catch (SQLException throwables) {
            System.out.println("Unable to retrieve flights. Please contact your administrator...");
            throwables.printStackTrace();
        }

        List<String> flightStrings = flights.stream().map(this::getRouteString).collect(Collectors.toList());
        flightStrings.add("Go back...");

        setOptions(flightStrings.toArray(new String[0]));

        setPrompt("Selection: ", 1, flights.size() + 1);
        int selection = printMenu();
        if(selection <= flights.size()) {
            FlightOptionsMenu flightOptionsMenu = new FlightOptionsMenu(this, flights.get(selection - 1));
            flightOptionsMenu.start();
        } else back();
    }

}
