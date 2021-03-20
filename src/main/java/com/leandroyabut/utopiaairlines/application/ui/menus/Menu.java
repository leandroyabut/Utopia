package com.leandroyabut.utopiaairlines.application.ui.menus;

import com.leandroyabut.utopiaairlines.application.entity.flight.Flight;
import com.leandroyabut.utopiaairlines.application.entity.flight.Route;
import com.leandroyabut.utopiaairlines.application.ui.MenuHelper;

public abstract class Menu {

    private Menu previousMenu;
    private MenuHelper helper;

    public Menu(Menu invokingMenu) {
        previousMenu = invokingMenu;
        helper = new MenuHelper();
    }

    public abstract void start();

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
}
