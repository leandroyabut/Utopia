package com.leandroyabut.utopiaairlines.application.ui.menus.administrator;

import com.leandroyabut.utopiaairlines.application.entity.user.User;
import com.leandroyabut.utopiaairlines.application.entity.user.UserRole;
import com.leandroyabut.utopiaairlines.application.service.dao.user.UserDAO;
import com.leandroyabut.utopiaairlines.application.service.handler.DAOHandler;
import com.leandroyabut.utopiaairlines.application.ui.menus.Menu;

import java.sql.SQLException;
import java.util.List;

public class UserCRUDMenu extends Menu {

    UserDAO userDAO;
    List<User> users;

    public UserCRUDMenu(Menu invokingMenu) {
        super(invokingMenu);
        setTitle("Users");
        setOptions("Add", "Update", "Delete", "Save", "Cancel...");
        setPrompt("Selection: ", 1, 5);
        userDAO = DAOHandler.getInstance().getUserDAO();
    }

    @Override
    public void start() {

        try {
            users = userDAO.getUsers();

            StringBuilder sb = new StringBuilder();
            users.forEach(user -> {

                String username = user.getUsername();
                String userType = user.getRole().getName();
                int userId = user.getId();

                sb.append(userId).append(") Username: ").append(username).append("\tRole: ").append(userType).append("\n");

            });
            setMessage(sb.toString());

        } catch (SQLException throwables) {
            System.out.println("Unable to retrieve users...");
        }

        int selection = printMenu();

        switch(selection) {

            case 1:
                try {
                    addUser();
                } catch (SQLException throwables) {
                    DAOHandler.getInstance().rollback();
                    System.out.println("Unable to add user...");
                    throwables.printStackTrace();
                }
                break;

            case 2:
                try {
                    updateUser();
                } catch (SQLException throwables) {
                    DAOHandler.getInstance().rollback();
                    System.out.println("Unable to update user...");
                }
                break;

            case 3:
                try {
                    deleteUser();
                } catch (SQLException throwables) {
                    DAOHandler.getInstance().rollback();
                    System.out.println("Unable to delete user...");
                }
                break;

            case 4:
                try {
                    DAOHandler.getInstance().commit();
                } catch (SQLException throwables) {
                    DAOHandler.getInstance().rollback();
                    System.out.println("Unable to save changes...");
                }
                break;

            case 5:
                DAOHandler.getInstance().rollback();
                back();
                break;

        }

        start();

    }

    private void addUser() throws SQLException {

        UserRole userRole = userDAO.getUserRoleById(getHelper().promptForInt("What kind of user? (1 - Employee, 2 - Administrator, 3 - Traveler): ", 1, 3));
        String givenName = getHelper().promptForString("First Name: ");
        String familyName = getHelper().promptForString("Last Name: ");
        String username = getHelper().promptForString("Username: ");
        String email = getHelper().promptForString("Email: ");
        String password = getHelper().promptForString("Password: ");
        String phone = getHelper().promptForString("Phone: ");

        userDAO.addUser(0, userRole.getId(), givenName, familyName, username, email, password, phone);

    }

    private void updateUser() throws SQLException {

        int id = getHelper().promptForInt("Enter user id # to update: ", 1, Integer.MAX_VALUE);
        String columnLabel = getHelper().promptForString("Enter column to edit: ");
        String value = getHelper().promptForString("Enter new value: ");

        if(userDAO.userExists(id))
            userDAO.updateUser(id, columnLabel, value);
        else System.out.println("Can't update a flight that doesn't exist...");

    }

    private void deleteUser() throws SQLException {
        int id = getHelper().promptForInt("Enter user id # to delete: ", 1, Integer.MAX_VALUE);

        if(userDAO.userExists(id)) {
            userDAO.deleteUser(id);
        } else System.out.println("Can't delete a user that does not exist...");
    }
}
