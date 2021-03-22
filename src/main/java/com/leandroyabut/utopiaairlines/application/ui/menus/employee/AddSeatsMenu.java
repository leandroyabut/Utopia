package com.leandroyabut.utopiaairlines.application.ui.menus.employee;

import com.leandroyabut.utopiaairlines.application.entity.flight.Flight;
import com.leandroyabut.utopiaairlines.application.service.dao.flight.FlightDAO;
import com.leandroyabut.utopiaairlines.application.service.handler.DAOHandler;
import com.leandroyabut.utopiaairlines.application.ui.menus.Menu;

import java.sql.SQLException;

public class AddSeatsMenu extends Menu {

    private Flight flight;
    private Flight currentFlight;
    private FlightDAO flightDAO;

    public AddSeatsMenu(Menu invokingMenu, Flight flight) {
        super(invokingMenu);
        this.flight = flight;
        this.currentFlight = flight;
        this.flightDAO = DAOHandler.getInstance().getFlightDAO();

        setTitle("Add Seats to Flight");
        setMessage("Select the seat class you are editing in your flight. (Use negative to subtract seats).");
        setOptions("First Class", "Business", "Economy", "Cancel...");
        setPrompt("Selection: ", 1, 4);

    }

    @Override
    public void start() {

        int selection = printMenu();
        try {
            currentFlight = flightDAO.getFlightById(flight.getId());
        } catch (SQLException throwables) {
            System.out.println("Unable to retrieve flight. Please contact your administrator.");
        }

        switch (selection) {

            case 1:
                updateFlightSeats("first");
                break;
            case 2:
                updateFlightSeats("business");
                break;
            case 3:
                updateFlightSeats("economy");
                break;

        }

        back();

    }

    private void updateFlightSeats(String seatClass) {

        String columnName = "reserved_" + seatClass + "_seats";

        int currentSeatLimit = 0;
        int currentReservedSeats = 0;

        switch (seatClass) {
            case "economy":
                currentSeatLimit = currentFlight.getAirplane().getType().getMaxEconomyCapacity();
                currentReservedSeats = currentFlight.getEconomyReservedSeats();
                break;
            case "first":
                currentSeatLimit = currentFlight.getAirplane().getType().getMaxFirstCapacity();
                currentReservedSeats = currentFlight.getFirstReservedSeats();
                break;
            case "business":
                currentSeatLimit = currentFlight.getAirplane().getType().getMaxBusinessCapacity();
                currentReservedSeats = currentFlight.getBusinessReservedSeats();
                break;
        }

        System.out.println("Current reserved seats: " + currentReservedSeats);

        int seatLimit = currentSeatLimit - currentReservedSeats;
        int newSeats = getHelper().promptForInt("Enter number of seats to reserve: ", -1*currentReservedSeats, seatLimit);

        try {
            flightDAO.updateFlight(currentFlight.getId(), columnName, currentReservedSeats + newSeats);
            flightDAO.commit();
        } catch (SQLException throwables) {
            System.out.println("Unable to update flight. Please contact administrator.");
            throwables.printStackTrace();
        }

    }
}
