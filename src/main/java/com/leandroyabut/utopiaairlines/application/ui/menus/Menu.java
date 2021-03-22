package com.leandroyabut.utopiaairlines.application.ui.menus;

import com.leandroyabut.utopiaairlines.application.entity.flight.Flight;
import com.leandroyabut.utopiaairlines.application.entity.flight.Route;
import com.leandroyabut.utopiaairlines.application.entity.user.User;
import com.leandroyabut.utopiaairlines.application.ui.MenuHelper;
import com.leandroyabut.utopiaairlines.application.ui.menus.util.RunOperation;

public abstract class Menu {

    private Menu previousMenu;
    private MenuHelper helper;
    private User user;

    private String title;
    private String message;
    private String[] options;
    private Object[] prompts;
    private String prompt;
    private int minIn = 0, maxIn = 0;

    public Menu(Menu invokingMenu) {
        previousMenu = invokingMenu;
        helper = new MenuHelper();
    }

    public abstract void start();

    private void printTitle() {
        if(title!=null) System.out.println(title);
    }

    private void printMessage() {
        if(message!=null) System.out.println(message);
    }

    private void printOptions() {
        if(options!=null) getHelper().printOptions(options);
    }

    private int printPrompt() {
        if(prompt!=null) return getHelper().promptForInt(prompt, minIn, maxIn);
        return -1;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setOptions(String... options) {
        this.options = options;
    }

    public void setPrompt(String message, int min, int max) {
        this.prompt = message;
        this.minIn = min;
        this.maxIn = max;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public int printMenu() {
        printTitle();
        System.out.println("---------------------------------");
        printMessage();
        System.out.println();
        printOptions();
        System.out.println();
        int selection = printPrompt();
        System.out.println("\n-------------------------------\n\n\n\n\n\n");
        return selection;
    }

    public void back() {
        if(previousMenu!=null) previousMenu.start();
    }

    public MenuHelper getHelper() {
        return helper;
    }

    public String getRouteString(Flight flight) {
        Route route = flight.getRoute();
        String originId = route.getOriginAirport().getAirportCode();
        String originCity = route.getOriginAirport().getCity();
        String destId = route.getDestinationAirport().getAirportCode();
        String destCity = route.getDestinationAirport().getCity();
        return String.format("%s, %s -> %s, %s", originId, originCity, destId, destCity);
    }

    public String getRouteString(Route route) {
        String originId = route.getOriginAirport().getAirportCode();
        String originCity = route.getOriginAirport().getCity();
        String destId = route.getDestinationAirport().getAirportCode();
        String destCity = route.getDestinationAirport().getCity();
        return String.format("%s, %s -> %s, %s", originId, originCity, destId, destCity);
    }

    public void promptForTextInput(String prompt, RunOperation operation, Runnable quitRunnnable) {
        System.out.println(prompt);
        String textInput = getHelper().promptForString("> ");

        if(textInput.equalsIgnoreCase("quit")) quitRunnnable.run();
        else operation.run(textInput);
    }
}
