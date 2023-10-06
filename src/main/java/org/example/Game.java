package org.example;

import java.time.LocalDate;

public class Game {

    private LocalDate date;
    private Team away;
    private Team home;
    private boolean bye;

    public Game(Team away, Team home, boolean bye) {
        this.away = away;
        this.home = home;
        this.bye = bye;
    }

    public Game(Team away, Team home) {
        this(away, home, false);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Team getAway() {
        return away;
    }

    public void setAway(Team away) {
        this.away = away;
    }

    public Team getHome() {
        return home;
    }

    public void setHome(Team home) {
        this.home = home;
    }

    public boolean isBye() {
        return bye;
    }

    public void setBye(boolean bye) {
        this.bye = bye;
    }

    @Override
    public String toString() {
        return date.toString() + " - " + away.getName() + " @ " + home.getName();
    }
}
