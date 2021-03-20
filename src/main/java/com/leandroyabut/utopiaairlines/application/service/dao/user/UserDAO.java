package com.leandroyabut.utopiaairlines.application.service.dao.user;

import com.leandroyabut.utopiaairlines.application.entity.user.*;
import com.leandroyabut.utopiaairlines.application.service.dao.DataAccessObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends DataAccessObject {
    public UserDAO() throws SQLException {
    }

    public UserRole getUserRoleById(int id) throws SQLException {
        UserRole role = null;

        ResultSet resultSet = query("select * from user_role where id = ?", id);

        if(resultSet.next()) {
            role = new UserRole(resultSet.getInt("id"), resultSet.getString("name"));
        }

        return role;
    }

    public User getUserById(int id) throws SQLException {
        User user = null;

        ResultSet resultSet = query("select * from user where id = ?", id);

        if(resultSet.next()) {
            user = new User(
                    resultSet.getInt("id"),
                    getUserRoleById(resultSet.getInt("role_id")),
                    resultSet.getString("given_name"),
                    resultSet.getString("family_name"),
                    resultSet.getString("username"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("phone")
            );
        }

        return user;
    }

    public void addUser(User user) throws SQLException {
        if(!userExists(user.getId()))
            update("insert into user values(?, ?, ?, ?, ?, ?, ?, ?)", user.getId(), user.getRole().getId(), user.getGivenName(), user.getFamilyName(), user.getUsername(), user.getEmail(), user.getPassword(), user.getPhone());
    }

    public void deleteUser(int id) throws SQLException {
        if(userExists(id))
            update("delete from user where id = ?", id);
    }

    public void updateUser(int id, String columnLabel, Object value) throws SQLException {
        if(userExists(id))
            update("update user set " + whiteListedColumnName(columnLabel) + " = ? where id = ?", value, id);
    }

    public boolean userExists(int id) throws SQLException {
        return query("select id from user where id = ?", id).next();
    }
}
