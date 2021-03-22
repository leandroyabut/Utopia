package com.leandroyabut.utopiaairlines.application.entity.airplane;

public class AirplaneType {

    private int id;
    private int maxCapacity;
    private int maxEconomyCapacity;
    private int maxFirstCapacity;
    private int maxBusinessCapacity;

    public AirplaneType(int id, int maxCapacity, int maxFirstCapacity, int maxBusinessCapacity) {
        this.id = id;
        this.maxCapacity = maxCapacity;
        this.maxFirstCapacity = maxFirstCapacity;
        this.maxBusinessCapacity = maxBusinessCapacity;
        this.maxEconomyCapacity = maxCapacity - maxFirstCapacity - maxBusinessCapacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getMaxFirstCapacity() {
        return maxFirstCapacity;
    }

    public void setMaxFirstCapacity(int maxFirstCapacity) {
        this.maxFirstCapacity = maxFirstCapacity;
    }

    public int getMaxBusinessCapacity() {
        return maxBusinessCapacity;
    }

    public void setMaxBusinessCapacity(int maxBusinessCapacity) {
        this.maxBusinessCapacity = maxBusinessCapacity;
    }

    public int getMaxEconomyCapacity() {
        return maxEconomyCapacity;
    }

    public void setMaxEconomyCapacity(int maxEconomyCapacity) {
        this.maxEconomyCapacity = maxEconomyCapacity;
    }
}
