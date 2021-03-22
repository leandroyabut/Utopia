package com.leandroyabut.utopiaairlines.application.ui.menus;

import com.leandroyabut.utopiaairlines.application.service.handler.DAOHandler;
import com.leandroyabut.utopiaairlines.application.ui.menus.account.LoginMenu;

import java.sql.SQLException;

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
            case 2:
            case 3:
                LoginMenu loginMenu;
                try {
                    loginMenu = new LoginMenu(this, DAOHandler.getInstance().getUserDAO().getUserRoleById(selection));
                    loginMenu.start();
                } catch (SQLException throwables) {
                    System.out.println("Unable to login...");
                }
                break;

            case 4:
                System.exit(0);
                break;

        }

    }
}
