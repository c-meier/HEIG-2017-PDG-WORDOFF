package ch.heigvd.wordoff.common.Dto.Mode;

public enum ModeType {
    // Duel between two player
    FRIEND_DUEL, // The opponent is chosen by the initiator of the mode.
    RANDOM_DUEL, // The opponent is randomly chosen.

    // Tournament last multiple days. Each day, all participants play a game against an AI.
    COMPETITIVE_TOURNAMENT, // The player joins a tournament created by the system.
    FRIENDLY_TOURNAMENT // The initiator choose the participants.
}
