package com.leandroyabut.utopiaairlines.application.ui.menus.administrator;

import com.leandroyabut.utopiaairlines.application.entity.flight.Airport;
import com.leandroyabut.utopiaairlines.application.service.dao.flight.FlightDAO;
import com.leandroyabut.utopiaairlines.application.service.handler.DAOHandler;
import com.leandroyabut.utopiaairlines.application.ui.menus.Menu;

import java.sql.SQLException;
import java.util.List;

public class AirportCRUDMenu extends Menu {

    List<Airport> airports;

    public AirportCRUDMenu(Menu invokingMenu) {
        super(invokingMenu);
        setTitle("Airports");
        setOptions("Add", "Update", "Delete", "Save", "Cancel...");
        setPrompt("Selection: ", 1, 5);
    }

    @Override
    public void start() {

        try {

            airports = DAOHandler.getInstance().getFlightDAO().getAirports();

            StringBuilder sb = new StringBuilder();
            airports.forEach(airport -> {
                sb.append("[");
                sb.append(airport.getAirportCode());
                sb.append("]    ");
                sb.append(airport.getCity());
                sb.append("\n");
            });

            setMessage(sb.toString());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Unable to retrieve airport. Please contact your administrator.");
        }

        int selection = printMenu();

        switch (selection) {

            case 1:
                String newCode = getHelper().promptForString("Enter 3 letter airport code: ");
                String newCity = getHelper().promptForString("Enter city name: ");
                addAirport(newCode, newCity);
                break;

            case 2:
                String updateCode = getHelper().promptForString("Enter airport code to edit: ");
                updateAirport(updateCode);
                break;

            case 3:
                String deleteCode = getHelper().promptForString("Enter airport code to delete: ");
                deleteAirport(deleteCode);
                break;

            case 4:
                try {
                    DAOHandler.getInstance().commit();
                } catch (SQLException throwables) {
                    DAOHandler.getInstance().rollback();
                    System.out.println("Unable to save these changes...");
                }
                break;

            case 5:
                DAOHandler.getInstance().rollback();
                back();
                break;

        }

        start();

    }

    private void addAirport(String code, String city) {

        FlightDAO flightDAO = DAOHandler.getInstance().getFlightDAO();

        Airport airport = new Airport(code, city);

        try {
            if(flightDAO.airportExists(code)) {
                System.out.println("Flight already exists...");
            } else {
                flightDAO.addAirport(airport);
            }
        } catch (SQLException throwables) {
            DAOHandler.getInstance().rollback();
            throwables.printStackTrace();
        }

    }

    private void updateAirport(String code) {

        FlightDAO flightDAO = DAOHandler.getInstance().getFlightDAO();

        try {
            if(!flightDAO.airportExists(code)) {
                System.out.println("Cannot update an airport that doesn't exist...");
            } else {
                String columnName = getHelper().promptForString("Enter PRIMARY KEY to edit: ");
                String value = getHelper().promptForString("Enter new value: ");
                flightDAO.updateAirport(code, columnName, value);
            }
        } catch (SQLException throwables) {
            DAOHandler.getInstance().rollback();
            System.out.println("Unable to update airport.");
        }

    }

    private void deleteAirport(String code) {
        FlightDAO flightDAO = DAOHandler.getInstance().getFlightDAO();

        try {
            if(!flightDAO.airportExists(code)) {
                System.out.println("Cannot delete an airport that doesn't exist...");
            } else {
                flightDAO.deleteAirport(code);
            }
        } catch (SQLException throwables) {
            DAOHandler.getInstance().rollback();
            System.out.println("Unable to delete airport.");
        }
    }
}
