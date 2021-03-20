package com.leandroyabut.utopiaairlines.application.service.dao;

import com.leandroyabut.utopiaairlines.application.util.ColumnWhitelist;
import com.leandroyabut.utopiaairlines.application.util.DatabaseCredentials;

import java.sql.*;
import java.util.List;

public class DataAccessObject {

    private final Connection connection;
    private final ColumnWhitelist columnWhitelist;

    public DataAccessObject() throws SQLException {

        DatabaseCredentials credentials = DatabaseCredentials.createFromFile("config/credentials.json");
        columnWhitelist = new ColumnWhitelist();

        try {
            Class.forName(credentials.getDriver());
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver was not found. Please check your dependencies.");
            e.printStackTrace();
        }

        connection = DriverManager.getConnection(credentials.getUrl(), credentials.getUsername(), credentials.getPassword());

        connection.setAutoCommit(false);
    }

    public String whiteListedColumnName(String columnName) {
        List<String> list = columnWhitelist.getWhitelist();
        if(list.contains(columnName)) return columnName;
        else return "";
    }

    protected void update(String sql, Object... objs) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement(sql);

        int count = 1;

        for(Object obj : objs) {
            statement.setObject(count, obj);
            count++;
        }

        statement.executeUpdate();
    }

    protected ResultSet query(String sql, Object... objs) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement(sql);

        int count = 1;

        for(Object obj : objs) {
            statement.setObject(count, obj);
            count++;
        }

        return statement.executeQuery();
    }

    public void commit() throws SQLException {
        getConnection().commit();
    }

    public Connection getConnection() {
        return connection;
    }

}
