package com.leandroyabut.utopiaairlines.application.ui.menus.employee;

import com.leandroyabut.utopiaairlines.application.entity.flight.Flight;
import com.leandroyabut.utopiaairlines.application.ui.menus.Menu;

public class EditFlightMenu extends Menu {

    Flight flight;

    public EditFlightMenu(Menu invokingMenu, Flight flight) {
        super(invokingMenu);
        this.flight = flight;
    }

    @Override
    public void start() {

        System.out.println("Flight: " + getRouteString(flight) + "\n");

        getHelper().printOptions(
                "View more details about the flight",
                "Update the details of the flight",
                "Add seats to flight",
                "Go back..."
                );

        System.out.println("\n");

        int selection = getHelper().promptForInt("Selection: ", 1, 4);

        System.out.println("\n\n");
        switch (selection) {
            case 1:
                break;
        }

    }


}
