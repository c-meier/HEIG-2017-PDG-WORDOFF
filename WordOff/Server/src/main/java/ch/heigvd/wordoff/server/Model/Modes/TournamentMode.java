/*
 * File: TournamentMode.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Model.Modes;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.Dto.InvitationStatus;
import ch.heigvd.wordoff.server.Model.Game;
import ch.heigvd.wordoff.server.Model.Invitation;
import ch.heigvd.wordoff.server.Model.Player;
import ch.heigvd.wordoff.server.Model.User;

import javax.persistence.Entity;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class representation of a tournament.
 */
@Entity
public class TournamentMode extends Mode {

    public TournamentMode() {}

    /**
     * Constructor for a tournament where the other players are not yet known.
     * @param participant The creator of the mode.
     * @param name The name of the tournament.
     */
    public TournamentMode(User participant, String name) {
        putInvitation(new Invitation(this, participant, InvitationStatus.ORIGIN, name));
    }

    /**
     * Constructor for a tournament where all possible participants are known at creation.
     * @param participants The list of (possible) participants of the mode. The first participant
     *                     MUST be the creator of the mode.
     * @param name The name of the tournament.
     */
    public TournamentMode(List<User> participants, String name) {
        Iterator<User> it = participants.listIterator();
        if(it.hasNext()) {
            // The creator of the mode
            putInvitation(new Invitation(this, it.next(), InvitationStatus.ORIGIN, name));
            while (it.hasNext()) {
                // The other invited participants.
                putInvitation(new Invitation(this, it.next(), InvitationStatus.WAITING, name));
            }
        }
    }

    /**
     * Get the current day of the tournament i.e. the number of days between now the start of the
     * tournament.
     * @return The current day.
     */
    private Long getCurrentDay() {
        return getStartDate() == null ? -1 : Duration.between(getStartDate(), LocalDateTime.now()).toDays();
    }

    /**
     * Check if the mode is ended.
     * @return True if there is no more day to the tournament, False otherwise.
     */
    @Override
    public boolean isEnded() {
        return getCurrentDay() >= Constants.TOURNAMENT_DURATION;
    }

    /**
     * Get the latest active game which concern the given user for the current day.
     * @param user The given user.
     * @return An Optional which contains the active game (if it exists).
     */
    @Override
    public Optional<Game> getActiveGame(User user) {
        return super.getActiveGame(user)
                .filter(g -> getCurrentDay() == Duration.between(getStartDate(), g.getStartDate()).toDays());
    }

    /**
     * Get the list of game which concern a given player
     * @param player the player
     * @return the list of games played by the player
     */
    public List<Game> getGamesOfPlayer(Player player) {
        return getGames()
                .stream()
                .filter(game -> Objects.equals(game.getSideInit().getPlayer().getId(), player.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Get the list of the games of the current day (today) which concern a given player.
     * @param player The player
     * @return The list of games
     */
    public List<Game> getGamesOfCurrentDayAndPlayer(Player player) {
        Long currentDay = getCurrentDay();
        return getGames()
                .stream()
                .filter(game -> Objects.equals(game.getSideInit().getPlayer().getId(), player.getId()))
                .filter(game -> currentDay == Duration.between(getStartDate(), game.getStartDate()).toDays())
                .collect(Collectors.toList());
    }

    /**
     * Get the list of games of a specific day
     * @param day The day
     * @return The list of games
     */
    public List<Game> getGamesOfDay(int day) {
        LocalDateTime start = getStartDate().plusDays(day);
        LocalDateTime end = getStartDate().plusDays(day + 1);
        return getGames()
                .stream()
                .filter(game -> game.getStartDate().isAfter(start) && game.getStartDate().isBefore(end))
                .collect(Collectors.toList());
    }

    /**
     * Get The list of all scores of all days for all players
     * @return A map with the user as the key and a list of score, each entry corresponding to th score of the day
     */
    public Map<User, List<Integer>> getAllPlayerScores() {
        // Group the games by day.
        Map<Long, List<Game>> days = getGames().stream()
                .filter(Game::isEnded)
                .collect(Collectors.groupingBy((Game g) -> Duration.between(getStartDate(), g.getStartDate()).toDays()));

        // Group the games by day and user.
        Map<Long, Map<Long, List<Game>>> daysAndUser = days
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                .stream()
                                .collect(Collectors.groupingBy(g -> g.getSideInit().getPlayer().getId()))
                ));

        return getInvitations()
                .stream()
                .filter(i -> i.getStatus() == InvitationStatus.ACCEPT || i.getStatus() == InvitationStatus.ORIGIN)
                .map(Invitation::getTarget)
                .collect(Collectors.toMap(u -> u, user -> IntStream
                        .range(0, Integer.min( // The number of effective days of the tournament.
                                (int)Duration.between(getStartDate(), LocalDateTime.now()).toDays() + 1,
                                Constants.TOURNAMENT_DURATION))
                        .map(i -> { // The score of the game corresponding to the day and the user.
                            if(daysAndUser.containsKey(i) && daysAndUser.get(i).containsKey(user.getId())) {
                                return daysAndUser
                                        .get(i)
                                        .get(user.getId())
                                        .stream()
                                        .max(Comparator.comparing(Game::getStartDate).reversed())
                                        .map(g -> g.getSideInit().getScore())
                                        .orElse(0);
                            } else {
                                return 0;
                            }})
                        .boxed()
                        .collect(Collectors.toList())
                ));
    }
}
