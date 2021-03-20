package com.leandroyabut.utopiaairlines.application.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class DatabaseCredentials {

    private final String driver;
    private final String url;
    private final String username;
    private final String password;

    public DatabaseCredentials(String driver, String url, String username, String password) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DatabaseCredentials createFromFile(String path) {
        DatabaseCredentials databaseCredentials = null;
        JSONParser jsonParser = new JSONParser();

        path = Objects.requireNonNull(DatabaseCredentials.class.getClassLoader().getResource(path)).getPath();

        try (FileReader reader = new FileReader(path)) {
            Object obj = jsonParser.parse(reader);

            JSONObject credentials = (JSONObject) obj;

            String driver = (String) credentials.get("driver");
            String url = (String) credentials.get("url");
            String username = (String) credentials.get("username");
            String password = (String) credentials.get("password");

            databaseCredentials = new DatabaseCredentials(driver, url, username, password);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return databaseCredentials;
    }


    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
