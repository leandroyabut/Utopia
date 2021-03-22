package com.leandroyabut.utopiaairlines.application.ui.menus.administrator;

import com.leandroyabut.utopiaairlines.application.ui.menus.Menu;

public class AdministratorMenu extends Menu {

    public AdministratorMenu(Menu invokingMenu) {
        super(invokingMenu);
        setTitle("Administrator Menu");
        setMessage("Add/Read/Update/Delete one of the following...");
        setOptions("Airports", "Flights", "Users", "Go back...");
        setPrompt("Selection: ", 1, 4);
    }

    @Override
    public void start() {
        int selection = printMenu();

        switch (selection) {

            case 1:
                AirportCRUDMenu airportCRUDMenu = new AirportCRUDMenu(this);
                airportCRUDMenu.start();
                break;

            case 2:
                FlightCRUDMenu flightCRUDMenu = new FlightCRUDMenu(this);
                flightCRUDMenu.start();
                break;

            case 3:
                UserCRUDMenu userCRUDMenu = new UserCRUDMenu(this);
                userCRUDMenu.setUser(getUser());
                userCRUDMenu.start();
                break;

            case 4:
                back();
                break;

        }
    }
}
