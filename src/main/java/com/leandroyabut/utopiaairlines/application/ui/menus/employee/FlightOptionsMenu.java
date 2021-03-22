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
        setMessage(getHelper().getFlightDetailsString(flight));
        setOptions(
                "Update the details of the flight",
                "Add seats to flight",
                "Go back..."
        );
        setPrompt("Selection: ", 1, 3);
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
                UpdateFlightMenu updateFlightMenu = new UpdateFlightMenu(this, currentFlight);
                updateFlightMenu.start();
                break;

            case 2:
                AddSeatsMenu addSeatsMenu = new AddSeatsMenu(this, currentFlight);
                addSeatsMenu.start();
                break;

            case 3:
                back();
                break;
        }

    }


}
