package com.leandroyabut.utopiaairlines.application.service.handler;

import com.leandroyabut.utopiaairlines.application.service.dao.DataAccessObject;
import com.leandroyabut.utopiaairlines.application.service.dao.booking.BookingDAO;
import com.leandroyabut.utopiaairlines.application.service.dao.flight.FlightDAO;
import com.leandroyabut.utopiaairlines.application.service.dao.flightbooking.FlightBookingDAO;
import com.leandroyabut.utopiaairlines.application.service.dao.user.UserDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOHandler implements AutoCloseable{

    private static DAOHandler instance;
    private BookingDAO bookingDAO;
    private FlightDAO flightDAO;
    private UserDAO userDAO;
    private FlightBookingDAO flightBookingDAO;

    private List<DataAccessObject> DAOs;

    private DAOHandler() {
        try {
            DAOs = new ArrayList<>();
            bookingDAO = new BookingDAO();
            flightDAO = new FlightDAO();
            userDAO = new UserDAO();
            flightBookingDAO = new FlightBookingDAO();

            DAOs.add(bookingDAO);
            DAOs.add(flightDAO);
            DAOs.add(userDAO);
            DAOs.add(flightBookingDAO);

        } catch (SQLException throwables) {
            System.out.println("SQL Error: DAO inaccessible...");
            throwables.printStackTrace();
        }
    }

    public static DAOHandler getInstance() {
        if(instance == null) {
            synchronized (DAOHandler.class) {
                if(instance == null) {
                    instance = new DAOHandler();
                }
            }
        }

        return instance;
    }

    public FlightDAO getFlightDAO() {
        return flightDAO;
    }

    public BookingDAO getBookingDAO() {
        return bookingDAO;
    }

    public FlightBookingDAO getFlightBookingDAO() {
        return flightBookingDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    /**
     * Commits all current data access objects
     * @throws SQLException query exception
     */
    public void commit() throws SQLException {
        for(DataAccessObject dao : DAOs) {
            if(dao != null) dao.getConnection().commit();
        }
    }

    /**
     * Commits the specified data access object
     * @param dao data access object to commit
     * @throws SQLException query exception
     */
    public void commit(DataAccessObject dao) throws SQLException {
        if(dao!=null) dao.getConnection().commit();
    }

    public void rollback() {
        for(DataAccessObject o : DAOs) {
            if(o!=null) {
                try {
                    o.getConnection().rollback();
                } catch (SQLException throwables) {
                    System.out.println("Unable to rollback...");
                    throwables.printStackTrace();
                }
            }
        }
    }

    @Override
    public void close() throws SQLException {
        for(DataAccessObject dao : DAOs) {
            if(dao!=null) dao.getConnection().close();
        }
    }
}

