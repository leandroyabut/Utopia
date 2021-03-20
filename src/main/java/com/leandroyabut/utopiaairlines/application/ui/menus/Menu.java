package com.leandroyabut.utopiaairlines.application.ui.menus;

public abstract class Menu {

    Menu previousMenu;

    public Menu(Menu invokingMenu) {
        previousMenu = invokingMenu;
    }

    abstract void start();

    public void back() {
        if(previousMenu!=null) previousMenu.start();
    }

}
