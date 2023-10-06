package org.example;

import java.util.ArrayList;
import java.util.List;

public class Week {

    private List<Game> games;

    public Week(List<Game> games) {
        this.games = games;
    }

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
        for (Game game : this.games) {
            sb.append("\n * ").append(game.toString());
        }
        return sb.toString();
    }
}
