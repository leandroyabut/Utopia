package com.leandroyabut.utopiaairlines.application.ui.menus;

import com.leandroyabut.utopiaairlines.application.ui.menus.administrator.AdministratorMenu;
import com.leandroyabut.utopiaairlines.application.ui.menus.employee.EmployeeMenu;

public class MainMenu extends Menu {

    public MainMenu() {
        super(null);
    }

    @Override
    public void start() {

        System.out.println("Welcome to the Utopia Airlines Management System...\n");

        getHelper().printOptions("Employee", "Administrator", "Traveler");

        int selection = getHelper().promptForInt("Which category of user are you? ", 1, 3);

        System.out.println("\n\n");

        switch(selection) {

            case 1:
                EmployeeMenu employeeMenu = new EmployeeMenu(this);
                employeeMenu.start();
                break;

            case 2:
                AdministratorMenu administratorMenu = new AdministratorMenu(this);
                administratorMenu.start();
                break;

        }

    }
}
