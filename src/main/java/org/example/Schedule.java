package org.example;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    public static final int WEEKS_IN_SEASON = 18;
    private List<Week> weeks = new ArrayList<>(WEEKS_IN_SEASON);

    public Schedule(List<Week> weeks) {
        this.weeks = weeks;
    }

    public List<Week> getWeeks() {
        if(weeks == null){
            weeks = new ArrayList<>();
        }
        return weeks;
    }

    public void setWeeks(List<Week> weeks) {
        this.weeks = weeks;
    }
}
