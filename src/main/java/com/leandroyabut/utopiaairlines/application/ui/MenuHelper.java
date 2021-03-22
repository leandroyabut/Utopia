package com.leandroyabut.utopiaairlines.application.ui;

import com.leandroyabut.utopiaairlines.application.entity.flight.Flight;
import com.leandroyabut.utopiaairlines.application.service.dao.flight.FlightDAO;
import com.leandroyabut.utopiaairlines.application.service.handler.DAOHandler;
import com.leandroyabut.utopiaairlines.application.ui.menus.Menu;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

        scanner.nextLine();
        return input;
    }

    public float promptForFloat(String message, float min, float max) {
        boolean received = false;

        float input = 0;

        while(!received) {
            System.out.print(message);

            if(scanner.hasNextFloat()) {
                input = scanner.nextFloat();
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

        scanner.nextLine();
        return input;
    }

    public String promptForString(String message) {
        System.out.flush();
        System.out.print(message);
        return scanner.nextLine();
    }

    public void printOptions(String... options) {
        int n = 1;
        for(String option : options) {
            System.out.println(n + ") " + option);
            n++;
        }
    }

    public Flight printFlights(Menu menu) {

        FlightDAO flightDAO = DAOHandler.getInstance().getFlightDAO();
        List<Flight> flights = new ArrayList<>();
        menu.setTitle("Flights");
        try {
            flights = flightDAO.getFlights();
        } catch (SQLException throwables) {
            System.out.println("Unable to retrieve flights. Please contact your administrator...");
        }

        List<String> flightStrings = flights.stream().map(menu::getRouteString).collect(Collectors.toList());
        flightStrings.add("Go back...");

        menu.setOptions(flightStrings.toArray(new String[0]));

        menu.setPrompt("Selection: ", 1, flights.size() + 1);

        int selection = menu.printMenu();

        if(selection <= flights.size())
            return flights.get(selection - 1);
        else {
            menu.back();
            return null;
        }
    }

    public String getFlightDetailsString(Flight flightObject) {
        Flight flight = flightObject;
        try {
            flight = DAOHandler.getInstance().getFlightDAO().getFlightById(flightObject.getId());
        } catch (SQLException throwables) {
            System.out.println("Unable to retrieve flight. Please contact administrator.");
        }
        LocalDateTimeStringConverter converter = new LocalDateTimeStringConverter();
        return String.format("Departure Airport: %s | Arrival Airport: %s\n" +
                        "Departure Date and Time: %s\n" +
                        "Arrival Date and Time: %s\n\n" +
                        "Available Seats per Class -\n" +
                        "First Class: %s\n" +
                        "Business Class: %s\n" +
                        "Economy Class: %s\n-----------------------------------------------------\n",
                flight.getRoute().getOriginAirport().getAirportCode(),
                flight.getRoute().getDestinationAirport().getAirportCode(),
                converter.toString(flight.getDepartureTime()),
                converter.toString(flight.getArrivalTime()),
                flight.getAirplane().getType().getMaxFirstCapacity() - flight.getFirstReservedSeats(),
                flight.getAirplane().getType().getMaxBusinessCapacity() - flight.getBusinessReservedSeats(),
                flight.getAirplane().getType().getMaxCapacity() - flight.getEconomyReservedSeats() - flight.getFirstReservedSeats() - flight.getBusinessReservedSeats());
    }

}
