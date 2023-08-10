package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.Conference.DIVISIONS_PER_CONFERENCE;
import static org.example.Division.TEAMS_PER_DIVISION;
import static org.example.League.CONFERENCES_PER_LEAGUE;
import static org.example.DivisionEnum.*;

public class Main {

    private static final int YEAR = 2023;

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String FILENAME = "nfl.json";

    public static void main(String[] args) {
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(FILENAME)) {
            Objects.requireNonNull(inputStream, "Input stream was null");
            League league = MAPPER.readValue(inputStream.readAllBytes(), League.class);

            List<Game> games = getDivisionGames(league);
            games.addAll(getConferenceGames(league));
            games.addAll(getInterConferenceGames(league));

            System.out.println("Total games: " + games.size());
        } catch (Exception e) {
            LOG.error(e.toString());
        }
    }

    private static List<Game> getDivisionGames(League league) {
        List<Game> games = new ArrayList<>();
        for (Conference conf : league.getConferences()) {
            for (Division div : conf.getDivisions()) {
                for (int i = 0; i < div.getTeams().size(); i++) {
                    for (int j = 0; j < div.getTeams().size(); j++) {
                        if (i != j) {
                            games.add(new Game(div.getTeams().get(i), div.getTeams().get(j)));
                        }
                    }
                }
            }
        }
        System.out.println("Divisional Games: " + games.size());
        return games;
    }

    private static List<Game> getConferenceGames(League league) throws Exception {
        List<Game> games = new ArrayList<>();
        for (Conference conf : league.getConferences()) {
            List<Division> divisions = conf.getDivisions();
            for (int i = 0; i < divisions.size(); i++) {

                Division div = divisions.get(i);
                int matchup = getIntraConferenceMatchup(i);
                Division otherDiv = divisions.get(matchup);

                for (int j = 0; j < TEAMS_PER_DIVISION; j++) {
                    for (int k = 0; k < TEAMS_PER_DIVISION; k++) {
                        if (i < matchup) {
                            if (j < TEAMS_PER_DIVISION / 2 && k < TEAMS_PER_DIVISION / 2) {
                                games.add(new Game(div.getTeams().get(j), otherDiv.getTeams().get(k)));
                            } else if (k < TEAMS_PER_DIVISION / 2) {
                                games.add(new Game(otherDiv.getTeams().get(k), div.getTeams().get(j)));
                            }
                        } else {
                            if (j >= TEAMS_PER_DIVISION / 2 && k < TEAMS_PER_DIVISION / 2) {
                                games.add(new Game(div.getTeams().get(j), otherDiv.getTeams().get(k)));
                            } else if (j >= TEAMS_PER_DIVISION / 2) {
                                games.add(new Game(otherDiv.getTeams().get(k), div.getTeams().get(j)));
                            }
                        }
                    }
                }

                Division oneOffAway = (matchup + 1) % TEAMS_PER_DIVISION == i
                    ? divisions.get((matchup + 2) % TEAMS_PER_DIVISION)
                    : divisions.get((matchup + 1) % TEAMS_PER_DIVISION);

                for (int n = 0; n < TEAMS_PER_DIVISION; n++) {
                    games.add(new Game(div.getTeams().get(n), oneOffAway.getTeams().get(n)));
                }
            }
        }

        System.out.println("Conference Games: " + games.size());
        return games;
    }

    private static List<Game> getInterConferenceGames(League league) {
        List<Game> games = get17thGames(league);

        for (int c = 0; c < CONFERENCES_PER_LEAGUE; c++) {
            for (int d = 0; d < CONFERENCES_PER_LEAGUE; d++) {
                if (c != d) {
                    Conference conf = league.getConferences().get(c);
                    Conference otherConf = league.getConferences().get(d);
                    for (int i = 0; i < DIVISIONS_PER_CONFERENCE; i++) {
                        int matchup = getInterConferenceMatchup(c, i);
                        Division div = conf.getDivisions().get(i);
                        Division otherDiv = otherConf.getDivisions().get(matchup);

                        for (int j = 0; j < TEAMS_PER_DIVISION; j++) {
                            for (int k = 0; k < TEAMS_PER_DIVISION; k++) {
                                if (i < matchup) {
                                    if (j < TEAMS_PER_DIVISION / 2 && k < TEAMS_PER_DIVISION / 2) {
                                        games.add(new Game(div.getTeams().get(j), otherDiv.getTeams().get(k)));
                                    } else if (k < TEAMS_PER_DIVISION / 2) {
                                        games.add(new Game(otherDiv.getTeams().get(k), div.getTeams().get(j)));
                                    }
                                } else {
                                    if (j >= TEAMS_PER_DIVISION / 2 && k < TEAMS_PER_DIVISION / 2) {
                                        games.add(new Game(div.getTeams().get(j), otherDiv.getTeams().get(k)));
                                    } else if (j >= TEAMS_PER_DIVISION / 2) {
                                        games.add(new Game(otherDiv.getTeams().get(k), div.getTeams().get(j)));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Interconference Games: " + games.size());
        return games;
    }

    private static List<Game> get17thGames(League league) {
        List<Game> games = new ArrayList<>();
        Conference home;
        Conference away;
        int homeNumber;
        if (YEAR % 2 == 0) {
            homeNumber = 0;
            home = league.getConferences().get(0);
            away = league.getConferences().get(1);
        } else {
            homeNumber = 1;
            home = league.getConferences().get(1);
            away = league.getConferences().get(0);
        }

        for (int i = 0; i < away.getDivisions().size(); i++) {
            Division awayDiv = away.getDivisions().get(i);
            Division homeDiv = home.getDivisions()
                .get((getInterConferenceMatchup(homeNumber, i) + 2) % DIVISIONS_PER_CONFERENCE);
            for (int j = 0; j < TEAMS_PER_DIVISION; j++) {
                games.add(new Game(awayDiv.getTeams().get(j), homeDiv.getTeams().get(j)));
            }
        }

        return games;
    }

    private static int getIntraConferenceMatchup(int div) {
        final int offset = YEAR % (DIVISIONS_PER_CONFERENCE - 1);
        if (offset == 2) {
            if (div == WEST.ordinal()) {
                return NORTH.ordinal();
            } else if (div == NORTH.ordinal()) {
                return WEST.ordinal();
            } else if (div == EAST.ordinal()) {
                return SOUTH.ordinal();
            } else if (div == SOUTH.ordinal()) {
                return EAST.ordinal();
            }
        } else if (offset == 1) {
            return (div + 2) % DIVISIONS_PER_CONFERENCE;
        } else if (offset == 0) {
            if (div == WEST.ordinal()) {
                return SOUTH.ordinal();
            } else if (div == NORTH.ordinal()) {
                return EAST.ordinal();
            } else if (div == EAST.ordinal()) {
                return NORTH.ordinal();
            } else if (div == SOUTH.ordinal()) {
                return WEST.ordinal();
            }
        }

        throw new RuntimeException("Each conference must have 4 divisions for this to work!");
    }

    private static int getInterConferenceMatchup(int conf, int div) {
        final int offset = YEAR % 4;
        if (offset == 0) {
            if (div == WEST.ordinal()) {
                return conf == 0 ? EAST.ordinal() : SOUTH.ordinal();
            } else if (div == NORTH.ordinal()) {
                return conf == 0 ? SOUTH.ordinal() : EAST.ordinal();
            } else if (div == EAST.ordinal()) {
                return conf == 0 ? NORTH.ordinal() : WEST.ordinal();
            } else if (div == SOUTH.ordinal()) {
                return conf == 0 ? WEST.ordinal() : NORTH.ordinal();
            }
        } else if (offset == 1) {
            if (div == WEST.ordinal()) {
                return conf == 0 ? SOUTH.ordinal() : WEST.ordinal();
            } else if (div == NORTH.ordinal()) {
                return NORTH.ordinal();
            } else if (div == EAST.ordinal()) {
                return conf == 0 ? WEST.ordinal() : SOUTH.ordinal();
            } else if (div == SOUTH.ordinal()) {
                return conf == 0 ? EAST.ordinal() : WEST.ordinal();
            }
        } else if (offset == 2) {
            if (div == WEST.ordinal()) {
                return WEST.ordinal();
            } else if (div == NORTH.ordinal()) {
                return conf == 0 ? EAST.ordinal() : SOUTH.ordinal();
            } else if (div == EAST.ordinal()) {
                return conf == 0 ? SOUTH.ordinal() : NORTH.ordinal();
            } else if (div == SOUTH.ordinal()) {
                return conf == 0 ? NORTH.ordinal() : EAST.ordinal();
            }
        } else if (offset == 3) {
            if (div == WEST.ordinal()) {
                return NORTH.ordinal();
            } else if (div == NORTH.ordinal()) {
                return WEST.ordinal();
            } else if (div == EAST.ordinal()) {
                return EAST.ordinal();
            } else if (div == SOUTH.ordinal()) {
                return SOUTH.ordinal();
            }
        }

        throw new RuntimeException("Each conference must have 4 divisions for this to work!");
    }
}