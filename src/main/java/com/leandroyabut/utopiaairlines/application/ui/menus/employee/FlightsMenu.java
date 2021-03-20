package com.leandroyabut.utopiaairlines.application.ui.menus.employee;

import com.leandroyabut.utopiaairlines.application.entity.flight.Flight;
import com.leandroyabut.utopiaairlines.application.entity.flight.Route;
import com.leandroyabut.utopiaairlines.application.service.dao.flight.FlightDAO;
import com.leandroyabut.utopiaairlines.application.service.handler.DAOHandler;
import com.leandroyabut.utopiaairlines.application.ui.menus.Menu;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class FlightsMenu extends Menu {

    private FlightDAO flightDAO;

    public FlightsMenu(Menu invokingMenu) {
        super(invokingMenu);
        flightDAO = DAOHandler.getInstance().getFlightDAO();
    }

    @Override
    public void start() {
        System.out.println("Flights: \n");
        try {
            List<Flight> flights = flightDAO.getFlights();
            List<String> flightStrings = flights.stream().map(flight -> getRouteString(flight)).collect(Collectors.toList());

            flightStrings.add("Go back...");
            getHelper().printOptions(flightStrings.toArray(new String[0]));

            int selection = getHelper().promptForInt("Select the flight you manage: ", 1, flightStrings.size());

            System.out.println("\n\n");

            if(selection < flights.size()) {
                EditFlightMenu editFlightMenu = new EditFlightMenu(this, flights.get(selection - 1));
                editFlightMenu.start();
            } else back();

        } catch (SQLException throwables) {
            System.out.println("Unable to retrieve flights. Please contact your administrator.");
            throwables.printStackTrace();
        }
    }


}
