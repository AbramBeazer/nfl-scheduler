package org.example;

import java.util.ArrayList;
import java.util.List;

public class Conference {
    public static final int DIVISIONS_PER_CONFERENCE = 4;

    private String name;
    private String abbreviation;
    private List<Division> divisions = new ArrayList<>(DIVISIONS_PER_CONFERENCE);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public List<Division> getDivisions() {
        return divisions;
    }

    public void setDivisions(List<Division> divisions) {
        this.divisions = divisions;
    }
}
