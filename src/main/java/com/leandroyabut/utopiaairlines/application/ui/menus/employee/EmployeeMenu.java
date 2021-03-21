package com.leandroyabut.utopiaairlines.application.ui.menus.employee;

import com.leandroyabut.utopiaairlines.application.ui.menus.Menu;

public class EmployeeMenu extends Menu {

    public EmployeeMenu(Menu invokingMenu) {
        super(invokingMenu);
        setTitle("Employee Menu");
        setOptions("Enter flights you manage", "Go back...");
        setPrompt("Selection: ", 1, 2);
    }

    @Override
    public void start() {

        int selection = printMenu();

        switch (selection) {
            case 1:
                FlightsMenu flightsMenu = new FlightsMenu(this);
                flightsMenu.start();
                break;

            case 2:
                back();
                break;
        }
    }
}
