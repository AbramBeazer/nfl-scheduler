package org.example;

import java.util.ArrayList;
import java.util.List;

public class League {

    public static final int CONFERENCES_PER_LEAGUE = 2;
    private String name;
    private String abbreviation;
    private List<Conference> conferences = new ArrayList<>(CONFERENCES_PER_LEAGUE);

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

    public List<Conference> getConferences() {
        return conferences;
    }

    public void setConferences(List<Conference> conferences) {
        this.conferences = conferences;
    }
}
