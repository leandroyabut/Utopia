package com.leandroyabut.utopiaairlines.application.ui.menus.employee;

import com.leandroyabut.utopiaairlines.application.ui.menus.Menu;

public class EmployeeMenu extends Menu {

    public EmployeeMenu(Menu invokingMenu) {
        super(invokingMenu);
    }

    @Override
    public void start() {
        System.out.println("Employee Menu: \n");

        getHelper().printOptions("Enter flights your manage", "Go back...");

        int selection = getHelper().promptForInt("Selection: ", 1, 2);

        System.out.println("\n\n");

        switch (selection) {
            case 1:
                FlightsMenu flightsMenu = new FlightsMenu(this);
                flightsMenu.start();
                break;

            case 2:
                this.back();
                break;
        }
    }
}
