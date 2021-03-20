package com.leandroyabut.utopiaairlines.application;

import com.leandroyabut.utopiaairlines.application.ui.MenuHelper;

public class Main {

    public static void main(String[] args) {

        MenuHelper menuHelper = new MenuHelper();

        menuHelper.printOptions("Option 1", "Option 2", "Option 3");
        menuHelper.promptForInt("Selection: ", 1, 3);
        menuHelper.clearScreen();
        menuHelper.printOptions("Exit", "Exit", "Exit");
        menuHelper.promptForInt("Selection: ", 1, 3);

    }

}
