package org.example;

import java.util.ArrayList;
import java.util.List;

public class Division {

    public enum DivisionCode {
        WEST,
        NORTH,
        EAST,
        SOUTH
    }

    public static final int TEAMS_PER_DIVISION = 4;

    private DivisionCode divisionCode;
    private String name;
    private List<Team> teams = new ArrayList<>(TEAMS_PER_DIVISION);

    public DivisionCode getDivisionCode() {
        return divisionCode;
    }

    public void setDivisionCode(DivisionCode divisionCode) {
        this.divisionCode = divisionCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
