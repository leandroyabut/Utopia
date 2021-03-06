package com.leandroyabut.utopiaairlines.application.ui.menus.employee;

import com.leandroyabut.utopiaairlines.application.entity.flight.Flight;
import com.leandroyabut.utopiaairlines.application.ui.menus.Menu;

public class FlightsMenu extends Menu {

    public FlightsMenu(Menu invokingMenu) {
        super(invokingMenu);
    }

    @Override
    public void start() {
        Flight flight = getHelper().printFlights(this);

        FlightOptionsMenu flightOptionsMenu = new FlightOptionsMenu(this, flight);
        flightOptionsMenu.start();
    }

}
