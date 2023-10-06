package org.example;

import java.util.ArrayList;
import java.util.List;

public class Schedule {

    public static final int WEEKS_IN_SEASON = 18;
    private List<Week> weeks;

    public Schedule() {
    }

    public List<Week> getWeeks() {
        if (weeks == null) {
            weeks = new ArrayList<>(WEEKS_IN_SEASON);
        }
        return weeks;
    }

    public void setWeeks(List<Week> weeks) {
        this.weeks = weeks;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < weeks.size(); i++) {
            Week week = weeks.get(i);
            sb.append("\nWeek ").append(i + 1);
            sb.append(week.toString());
        }
        return sb.toString();
    }
}
