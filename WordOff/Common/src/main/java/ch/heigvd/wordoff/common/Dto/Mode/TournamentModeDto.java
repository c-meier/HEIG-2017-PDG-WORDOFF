package ch.heigvd.wordoff.common.Dto.Mode;

import ch.heigvd.wordoff.common.Dto.User.UserSummaryDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TournamentModeDto extends ModeDto {

    /**
     * The participants in the tournament. Represented as a map, the key is the summary of the user
     * and the value is a list containing the score for each day.
     */
    private Map<UserSummaryDto, List<Integer>> participants;

    public Map<UserSummaryDto, List<Integer>> getParticipants() {
        return participants;
    }

    public void setParticipants(Map<UserSummaryDto, List<Integer>> participants) {
        this.participants = participants;
    }

    @JsonIgnore
    public List<UserScore> getPlayerScoreForGlobal() {
        return participants.entrySet()
                .stream()
                .map(p -> new UserScore(
                        p.getKey(),
                        p.getValue().stream().mapToInt(Integer::intValue).sum()))
                .sorted(Comparator.comparing(UserScore::getScore).reversed())
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public List<UserScore> getPlayerScoreForDay(int day) {
        return participants.entrySet()
                .stream()
                .map(p -> new UserScore(p.getKey(), p.getValue().get(day)))
                .sorted(Comparator.comparing(UserScore::getScore).reversed())
                .collect(Collectors.toList());
    }

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
}
