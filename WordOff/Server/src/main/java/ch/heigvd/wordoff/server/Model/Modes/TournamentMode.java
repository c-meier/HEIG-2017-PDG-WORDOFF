package ch.heigvd.wordoff.server.Model.Modes;

import ch.heigvd.wordoff.server.Model.Game;
import ch.heigvd.wordoff.server.Model.Invitation;
import ch.heigvd.wordoff.server.Model.Player;
import ch.heigvd.wordoff.server.Model.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TournamentMode extends Mode {
    // The number of days of the tournament.
    private final int TOURNAMENT_DURATION = 5;

    @Override
    public boolean isEnded() {
        return Duration.between(getStartDate(), LocalDateTime.now()).toDays() < TOURNAMENT_DURATION;
    }

    public List<Game> getGamesOfPlayer(Player player) {
        return getGames()
                .stream()
                .filter(game -> Objects.equals(game.getSideInit().getPlayer().getId(), player.getId()))
                .collect(Collectors.toList());
    }

    public List<Game> getGamesOfDay(int day) {
        LocalDateTime start = getStartDate().plusDays(day);
        LocalDateTime end = getStartDate().plusDays(day + 1);
        return getGames()
                .stream()
                .filter(game -> game.getStartDate().isAfter(start) && game.getStartDate().isBefore(end))
                .collect(Collectors.toList());
    }

    public List<Player> getAllPLayers() {
        return getInvitations()
                .values()
                .stream()
                .map(Invitation::getTarget)
                .collect(Collectors.toList());
    }

    public Map<User, List<Integer>> getAllPlayerScores() {
        Map<User, List<Integer>> maps = new HashMap<>();
        getGames().stream()
                .filter(Game::isEnded)
                .sorted(Comparator.comparing(Game::getStartDate))
                .forEachOrdered(g -> {
                    User u = (User) g.getSideInit().getPlayer();
                    List<Integer> scores = maps.getOrDefault(u, new ArrayList<>());
                    scores.add(g.getSideInit().getScore());
                    maps.put(u, scores);
                });
        return maps;
    }
}
