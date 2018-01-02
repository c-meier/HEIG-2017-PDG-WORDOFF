package ch.heigvd.wordoff.common.Dto.Mode;

import ch.heigvd.wordoff.common.Dto.User.UserSummaryDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TournamentModeDto extends ModeDto {

    /**
     * The number of game that that can be played for the current day.
     */
    private int nbGameRemaining;

    /**
     * The participants in the tournament. Represented as a map, the key is the summary of the user
     * and the value is a list containing the score for each day.
     */
    private List<UserScores> participants;

    public int getNbGameRemaining() {
        return nbGameRemaining;
    }

    public void setNbGameRemaining(int nbGameRemaining) {
        this.nbGameRemaining = nbGameRemaining;
    }

    public List<UserScores> getParticipants() {
        return participants;
    }

    public void setParticipants(List<UserScores> participants) {
        this.participants = participants;
    }

    @JsonIgnore
    public List<UserScore> getPlayerScoreForGlobal() {
        return participants
                .stream()
                .map(p -> new UserScore(
                        p.getUser(),
                        p.getScores().stream().mapToInt(Integer::intValue).sum()))
                .sorted(Comparator.comparing(UserScore::getScore).reversed())
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public List<UserScore> getPlayerScoreForDay(int day) {
        return participants
                .stream()
                .map(p -> new UserScore(p.getUser(), p.getDayScore(day)))
                .sorted(Comparator.comparing(UserScore::getScore).reversed())
                .collect(Collectors.toList());
    }

    /**
     * A class pairing a User with a score.
     */
    public static class UserScore {
        private UserSummaryDto user;
        private int score;

        public UserScore(UserSummaryDto user, int score) {
            this.user = user;
            this.score = score;
        }

        public UserSummaryDto getUser() {
            return user;
        }

        public int getScore() {
            return score;
        }
    }

    /**
     * A class pairing a User with the scores for each day.
     */
    public static class UserScores {
        private UserSummaryDto user;
        private List<Integer> scores;

        public UserScores(UserSummaryDto user, List<Integer> scores) {
            this.user = user;
            this.scores = scores;
        }

        public UserSummaryDto getUser() {
            return user;
        }

        public List<Integer> getScores() {
            return scores;
        }

        public int getDayScore(int day) {
            return day < scores.size() ? scores.get(day) : 0;
        }
    }
}
