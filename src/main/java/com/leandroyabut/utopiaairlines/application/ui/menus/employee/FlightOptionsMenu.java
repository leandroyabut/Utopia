package com.leandroyabut.utopiaairlines.application.ui.menus.employee;

import com.leandroyabut.utopiaairlines.application.entity.flight.Flight;
import com.leandroyabut.utopiaairlines.application.service.handler.DAOHandler;
import com.leandroyabut.utopiaairlines.application.ui.menus.Menu;

import java.sql.SQLException;

public class FlightOptionsMenu extends Menu {

    private Flight flight;

    public FlightOptionsMenu(Menu invokingMenu, Flight flight) {
        super(invokingMenu);
        this.flight = flight;
        setOptions(
                "View more details about the flight",
                "Update the details of the flight",
                "Add seats to flight",
                "Go back..."
        );
        setPrompt("Selection: ", 1, 4);
    }

    @Override
    public void start() {

        Flight currentFlight = flight;
        try {
            currentFlight = DAOHandler.getInstance().getFlightDAO().getFlightById(flight.getId());
        } catch (SQLException throwables) {
            System.out.println("Unable to retrieve flight.");
        }

        try {
            setTitle("Flight: " + getRouteString(DAOHandler.getInstance().getFlightDAO().getFlightById(flight.getId())));
        } catch (SQLException throwables) {
            System.out.println("Unable to retrieve flight...");
        }

        int selection = printMenu();

        switch (selection) {
            case 1:
                FlightDetailsMenu flightDetails = new FlightDetailsMenu(this, currentFlight);
                flightDetails.start();
                break;

            case 2:
                UpdateFlightMenu updateFlightMenu = new UpdateFlightMenu(this, currentFlight);
                updateFlightMenu.start();
                break;

            case 4:
                back();
                break;
        }

    }


}
