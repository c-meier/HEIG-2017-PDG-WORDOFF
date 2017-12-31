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

@Entity
public class TournamentMode extends Mode {

    public TournamentMode() {}

    public TournamentMode(User participant, String name) {
        putInvitation(new Invitation(this, participant, InvitationStatus.ORIGIN, name));
    }

    public TournamentMode(List<User> participants, String name) {
        for (User u : participants) {
            if (u.equals(participants.get(0))) {
                putInvitation(new Invitation(this, u, InvitationStatus.ORIGIN, name));
            } else {
                putInvitation(new Invitation(this, u, InvitationStatus.WAITING, name));
            }
        }
    }

    private Long getCurrentDay() {
        return Duration.between(getStartDate(), LocalDateTime.now()).toDays();
    }

    @Override
    public boolean isEnded() {
        return getCurrentDay()  < Constants.TOURNAMENT_DURATION;
    }

    @Override
    public Optional<Game> getActiveGame(User user) {
        return super.getActiveGame(user)
                .filter(g -> getCurrentDay() == Duration.between(getStartDate(), g.getStartDate()).toDays());
    }

    public List<Game> getGamesOfPlayer(Player player) {
        return getGames()
                .stream()
                .filter(game -> Objects.equals(game.getSideInit().getPlayer().getId(), player.getId()))
                .collect(Collectors.toList());
    }

    public List<Game> getGamesOfCurrentDayAndPlayer(Player player) {
        Long currentDay = getCurrentDay();
        return getGames()
                .stream()
                .filter(game -> Objects.equals(game.getSideInit().getPlayer().getId(), player.getId()))
                .filter(game -> currentDay == Duration.between(getStartDate(), game.getStartDate()).toDays())
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

    public Map<Long, List<Integer>> getAllPlayerScores() {
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

        return getInvitations().values()
                .stream()
                .filter(i -> i.getStatus() == InvitationStatus.ACCEPT || i.getStatus() == InvitationStatus.ORIGIN)
                .map(Invitation::getTarget)
                .collect(Collectors.toMap(User::getId, user -> IntStream
                        .range(0, Integer.min( // The number of effective days of the tournament.
                                (int)Duration.between(getStartDate(), LocalDateTime.now()).toDays(),
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
