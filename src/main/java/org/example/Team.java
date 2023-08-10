package org.example;

public class Team {

    private String location;
    private String name;

    private int previousStanding;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPreviousStanding() {
        return previousStanding;
    }

    public void setPreviousStanding(int previousStanding) {
        this.previousStanding = previousStanding;
    }

    public String getFullTeamName() {
        return location + " " + name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Team) {
            Team other = (Team) obj;
            return this.location.equals(other.location) && this.name.equals(other.name);
        }
        return false;
    }

    @Override
    public String toString() {
        return getFullTeamName();
    }
}
