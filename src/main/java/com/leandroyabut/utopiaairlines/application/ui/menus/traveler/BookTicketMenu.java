package com.leandroyabut.utopiaairlines.application.ui.menus.traveler;

import com.leandroyabut.utopiaairlines.application.entity.booking.Booking;
import com.leandroyabut.utopiaairlines.application.entity.flight.Flight;
import com.leandroyabut.utopiaairlines.application.entity.user.User;
import com.leandroyabut.utopiaairlines.application.service.dao.booking.BookingDAO;
import com.leandroyabut.utopiaairlines.application.service.dao.flight.FlightDAO;
import com.leandroyabut.utopiaairlines.application.service.dao.flightbooking.FlightBookingDAO;
import com.leandroyabut.utopiaairlines.application.service.handler.DAOHandler;
import com.leandroyabut.utopiaairlines.application.ui.menus.Menu;
import com.leandroyabut.utopiaairlines.application.util.ConfirmationCodeGenerator;

import java.sql.SQLException;

public class BookTicketMenu extends Menu {

    private Flight flight;

    public BookTicketMenu(Menu invokingMenu, Flight flight) {
        super(invokingMenu);
        this.flight = flight;
    }

    @Override
    public void start() {
        try {
            this.flight = DAOHandler.getInstance().getFlightDAO().getFlightById(flight.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        setTitle("Book a Ticket");
        setOptions("Book First Class", "Book Business", "Book Economy", "Cancel...");
        setMessage(getHelper().getFlightDetailsString(flight));
        setPrompt("Selection: ", 1, 4);

        switch (printMenu()) {

            case 1:
                bookFlight(flight, getUser(), "first");
                break;

            case 2:
                bookFlight(flight, getUser(), "business");
                break;

            case 3:
                bookFlight(flight, getUser(), "economy");
                break;

            case 4:
                back();
                break;

        }

        back();
    }

    public void bookFlight(Flight flight, User user, String seatClass) {

        ConfirmationCodeGenerator generator = new ConfirmationCodeGenerator(flight.getId() * (int) System.currentTimeMillis());
        Booking booking = new Booking(true, generator.generate() + "/" + seatClass);

        BookingDAO bookingDAO = DAOHandler.getInstance().getBookingDAO();
        FlightDAO flightDAO = DAOHandler.getInstance().getFlightDAO();
        FlightBookingDAO flightBookingDAO = DAOHandler.getInstance().getFlightBookingDAO();

        try {

            bookingDAO.addBooking(booking);
            booking = bookingDAO.getBookingByConfirmationCode(booking.getConfirmationCode());
            bookingDAO.addBookingUser(booking, user);

            String columnLabel = "reserved_" + seatClass + "_seats";
            int currentReservedSeats = 0;

            switch(seatClass) {
                case "first":
                    currentReservedSeats = flight.getFirstReservedSeats();
                    break;

                case "business":
                    currentReservedSeats = flight.getBusinessReservedSeats();
                    break;

                case "economy":
                    currentReservedSeats = flight.getEconomyReservedSeats();
                    break;
            }

            flightDAO.updateFlight(flight.getId(), columnLabel, currentReservedSeats + 1);

            bookingDAO.commit();
            flightDAO.commit();

            flightBookingDAO.addFlightBooking(flight.getId(), booking.getId());
            flightBookingDAO.commit();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Unable to book flight. Please contact your administrator.");
        }

    }
}
