package com.leandroyabut.utopiaairlines.application.ui.menus.traveler;

import com.leandroyabut.utopiaairlines.application.entity.flight.Flight;
import com.leandroyabut.utopiaairlines.application.ui.menus.Menu;

public class FlightsMenu extends Menu {

    private int mode;

    public FlightsMenu(Menu invokingMenu) {
        super(invokingMenu);
    }

    @Override
    public void start() {
        Flight flight = getHelper().printFlights(this);

        BookTicketMenu bookTicketMenu = new BookTicketMenu(this, flight);
        bookTicketMenu.setUser(getUser());
        bookTicketMenu.start();
    }
}
