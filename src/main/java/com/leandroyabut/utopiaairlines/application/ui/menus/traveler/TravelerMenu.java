package com.leandroyabut.utopiaairlines.application.ui.menus.traveler;

import com.leandroyabut.utopiaairlines.application.ui.menus.Menu;

public class TravelerMenu extends Menu {

    public TravelerMenu(Menu invokingMenu) {
        super(invokingMenu);
        setTitle("Traveler Menu");
        setOptions("Book a Ticket", "Cancel an Upcoming Trip", "Go back...");
        setPrompt("Selection: ", 1, 3);
    }

    @Override
    public void start() {

        int selection = printMenu();

        switch (selection) {
            case 1:
                FlightsMenu flightsMenu = new FlightsMenu(this);
                flightsMenu.setUser(getUser());
                flightsMenu.start();
                break;

            case 2:
                BookingsMenu bookingsMenu = new BookingsMenu(this);
                bookingsMenu.setUser(getUser());
                bookingsMenu.start();
                break;

            case 3:
                back();
                break;

        }

    }
}
