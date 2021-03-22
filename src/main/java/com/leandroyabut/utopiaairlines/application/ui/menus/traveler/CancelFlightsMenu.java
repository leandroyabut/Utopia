package com.leandroyabut.utopiaairlines.application.ui.menus.traveler;

import com.leandroyabut.utopiaairlines.application.entity.FlightBooking;
import com.leandroyabut.utopiaairlines.application.service.dao.booking.BookingDAO;
import com.leandroyabut.utopiaairlines.application.service.dao.flight.FlightDAO;
import com.leandroyabut.utopiaairlines.application.service.dao.flightbooking.FlightBookingDAO;
import com.leandroyabut.utopiaairlines.application.service.handler.DAOHandler;
import com.leandroyabut.utopiaairlines.application.ui.menus.Menu;

import java.sql.SQLException;

public class CancelFlightsMenu extends Menu {

    private final FlightBookingDAO flightBookingDAO;
    private final FlightDAO flightDAO;
    private final BookingDAO bookingDAO;
    private final FlightBooking flightBooking;

    public CancelFlightsMenu(Menu invokingMenu, FlightBooking booking) {
        super(invokingMenu);
        flightBookingDAO = DAOHandler.getInstance().getFlightBookingDAO();
        flightDAO = DAOHandler.getInstance().getFlightDAO();
        bookingDAO = DAOHandler.getInstance().getBookingDAO();
        flightBooking = booking;
        setTitle("Cancel Flight: " + getRouteString(booking.getFlight()));
        setMessage("Are you sure you want to cancel this flight?");
        setOptions("Yes", "No");
        setPrompt("Selection: ", 1, 2);
    }

    @Override
    public void start() {

        int selection = printMenu();

        switch (selection) {
            case 1:
                try {
                    flightBookingDAO.deleteFlightBooking(flightBooking.getFlight().getId(), flightBooking.getBooking().getId());
                    flightBookingDAO.commit();

                    String seatClass = flightBooking.getBooking().getConfirmationCode().split("/")[1];

                    String columnLabel = "reserved_" + seatClass + "_seats";
                    int currentReservedSeats = 0;

                    switch(seatClass) {
                        case "first":
                            currentReservedSeats = flightBooking.getFlight().getFirstReservedSeats();
                            break;

                        case "business":
                            currentReservedSeats = flightBooking.getFlight().getBusinessReservedSeats();
                            break;

                        case "economy":
                            currentReservedSeats = flightBooking.getFlight().getEconomyReservedSeats();
                            break;
                    }

                    flightDAO.updateFlight(flightBooking.getFlight().getId(), columnLabel, currentReservedSeats - 1);

                    bookingDAO.deleteBooking(flightBooking.getBooking().getId());
                    bookingDAO.commit();

                } catch (SQLException throwables) {
                    System.out.println("Unable to delete...");
                }
                break;

            case 2:
                back();
                break;
        }

        back();

    }

}
