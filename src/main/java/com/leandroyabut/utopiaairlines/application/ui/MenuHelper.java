package com.leandroyabut.utopiaairlines.application.ui;

import java.util.Scanner;

public class MenuHelper {

    private Scanner scanner;

    public MenuHelper() {
        scanner = new Scanner(System.in);
    }

    public int promptForInt(String message, int min, int max) {
        boolean received = false;

        int input = 0;

        while(!received) {
            System.out.print(message);

            if(scanner.hasNextInt()) {
                input = scanner.nextInt();
                if(input >= min && input <= max) {
                    received = true;
                } else {
                    System.out.println("Input in not within range...");
                    received = false;
                }
            } else {
                System.out.println("Input is not a number...");
                break;
            }
        }

        return input;
    }

    public void printOptions(String... options) {
        int n = 1;
        for(String option : options) {
            System.out.println(n + ") " + option);
            n++;
        }
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
