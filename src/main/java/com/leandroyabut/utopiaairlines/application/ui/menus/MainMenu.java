package com.leandroyabut.utopiaairlines.application.ui.menus;

import com.leandroyabut.utopiaairlines.application.ui.menus.administrator.AdministratorMenu;
import com.leandroyabut.utopiaairlines.application.ui.menus.employee.EmployeeMenu;
import com.leandroyabut.utopiaairlines.application.ui.menus.traveler.TravelerMenu;

public class MainMenu extends Menu {

    public MainMenu() {
        super(null);
        setTitle("Utopia Airlines Management System");
        setMessage("Welcome to the Utopia Airlines Management System...");
        setOptions("Employee", "Administrator", "Traveler", "Exit...");
        setPrompt("Enter user type: ", 1, 4);
    }

    @Override
    public void start() {

        int selection = printMenu();

        switch(selection) {

            case 1:
                EmployeeMenu employeeMenu = new EmployeeMenu(this);
                employeeMenu.start();
                break;

            case 2:
                AdministratorMenu administratorMenu = new AdministratorMenu(this);
                administratorMenu.start();
                break;

            case 3:
                TravelerMenu travelerMenu = new TravelerMenu(this);
                travelerMenu.start();

            case 4:
                System.exit(0);
                break;

        }

    }
}
