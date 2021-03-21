package com.leandroyabut.utopiaairlines.application.ui.menus.employee;

import com.leandroyabut.utopiaairlines.application.entity.flight.Flight;
import com.leandroyabut.utopiaairlines.application.ui.menus.Menu;
import javafx.util.converter.LocalDateTimeStringConverter;

public class FlightDetailsMenu extends Menu {

    private Flight flight;

    public FlightDetailsMenu(Menu invokingMenu, Flight flight) {
        super(invokingMenu);
        this.flight = flight;
        LocalDateTimeStringConverter converter = new LocalDateTimeStringConverter();
        setTitle("Flight Details");
        String message = String.format("Departure Airport: %s | Arrival Airport: %s\n" +
                        "Departure Date and Time: %s\n" +
                        "Arrival Date and Time: %s\n\n" +
                        "Available Seats per class -\n" +
                        "First Class: %s\n" +
                        "Business Class: %s\n" +
                        "Economy Class: %s",
                flight.getRoute().getOriginAirport().getAirportCode(),
                flight.getRoute().getDestinationAirport().getAirportCode(),
                converter.toString(flight.getDepartureTime()),
                converter.toString(flight.getArrivalTime()),
                flight.getAirplane().getType().getMaxFirstCapacity() - flight.getFirstReservedSeats(),
                flight.getAirplane().getType().getMaxBusinessCapacity() - flight.getBusinessReservedSeats(),
                flight.getAirplane().getType().getMaxCapacity() - flight.getEconomyReservedSeats() - flight.getFirstReservedSeats() - flight.getBusinessReservedSeats());
        setMessage(message);
        setOptions("Go back...");
        setPrompt("Selection: ", 1, 1);
    }

    @Override
    public void start() {
        printMenu();
        back();
    }
}
