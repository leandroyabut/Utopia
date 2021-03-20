package com.leandroyabut.utopiaairlines.application.entity.airplane;

import java.util.Objects;

public class Airplane {
    private int id;
    private AirplaneType type;

    public Airplane(int id, AirplaneType type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AirplaneType getType() {
        return type;
    }

    public void setType(AirplaneType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airplane airplane = (Airplane) o;
        return id == airplane.id && type.equals(airplane.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }
}
