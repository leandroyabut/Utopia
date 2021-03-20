package com.leandroyabut.utopiaairlines.application.entity.airplane;

public class AirplaneType {
    private int id;
    private int maxCapacity;

    public AirplaneType(int id, int maxCapacity) {
        this.id = id;
        this.maxCapacity = maxCapacity;
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
}
