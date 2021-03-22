package com.leandroyabut.utopiaairlines.application.ui.menus.traveler;

import com.leandroyabut.utopiaairlines.application.entity.FlightBooking;
import com.leandroyabut.utopiaairlines.application.service.dao.flight.FlightDAO;
import com.leandroyabut.utopiaairlines.application.service.handler.DAOHandler;
import com.leandroyabut.utopiaairlines.application.ui.menus.Menu;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class BookingsMenu extends Menu {

    List<FlightBooking> bookings;
    FlightDAO flightDAO;

    public BookingsMenu(Menu invokingMenu) {
        super(invokingMenu);
    }

    @Override
    public void start() {

        flightDAO = DAOHandler.getInstance().getFlightDAO();
        try {
            bookings = DAOHandler.getInstance().getFlightBookingDAO().getFlightBookings(getUser());
        } catch (SQLException throwables) {
            System.out.println("Unable to retrieve flight bookings. Please contact your administrator.");
            throwables.printStackTrace();
        }

        setTitle("Your Bookings");

        List<String> bookingList = bookings.stream().map(fb -> getRouteString(fb.getFlight())).collect(Collectors.toList());
        bookingList.add("Go back...");
        String[] bookingStrings = bookingList.toArray(new String[0]);

        setOptions(bookingStrings);

        setPrompt("Selection: ", 1, bookingStrings.length + 1);

        int selection = printMenu();

        if(selection <= bookings.size()) {
            CancelFlightsMenu cancelFlightsMenu = new CancelFlightsMenu(this, bookings.get(selection - 1));
            cancelFlightsMenu.setUser(getUser());
            cancelFlightsMenu.start();
        } else back();

    }

}
