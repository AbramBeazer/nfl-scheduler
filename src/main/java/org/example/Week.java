package org.example;

import java.util.ArrayList;
import java.util.List;

public class Week {

    private List<Game> games;

    public List<Game> getGames() {
        if (games == null) {
            games = new ArrayList<>();
        }
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Game game : games) {
            sb.append(game.toString());
            sb.append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }
}
