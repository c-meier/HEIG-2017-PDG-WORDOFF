/*
 * File: GameDto.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common.Dto.Game;

import ch.heigvd.wordoff.common.Dto.Endpoint.IResource;
import ch.heigvd.wordoff.common.Dto.Endpoint.ResourceWriteList;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
import java.util.Objects;

/**
 * Project : WordOff
 * Date : 10.10.17
 */
public class GameDto implements IResource<GameDto> {
    private Long id;

    private SideDto mySide;

    @JsonSerialize(typing = JsonSerialize.Typing.STATIC)
    private OtherSideDto otherSide;

    private Date startDate;

    private String lang;

    private boolean myTurn;

    private int bagSize;

    private boolean ended;

    private boolean wordanalyser;

    private static Class<GameDto> resourceType = GameDto.class;
    /**
     * Endpoint to POST challenges.
     * Is used to play a word.
     */
    private ResourceWriteList<GameDto, ChallengeDto> challenges = new ResourceWriteList<>(GameDto.class, ChallengeDto.class);

    /**
     * Endpoint to refresh (GET) the game
     */
    private String endpoint;

    // Necessary for Jackson deserialization
    protected GameDto() {}

    public GameDto(Long id, SideDto mySide, OtherSideDto otherSide, boolean myTurn, String lang, Date startDate) {
        this.id = id;
        this.mySide = mySide;
        this.otherSide = otherSide;
        this.lang = lang;
        this.startDate = startDate;
        this.myTurn = myTurn;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public SideDto getMySide() {
        return mySide;
    }

    public void setMySide(SideDto mySide) {
        this.mySide = mySide;
    }

    public OtherSideDto getOtherSide() {
        return otherSide;
    }

    public void setOtherSide(OtherSideDto otherSide) {
        this.otherSide = otherSide;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getBagSize() {
        return bagSize;
    }

    public void setBagSize(int bagSize) {
        this.bagSize = bagSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public boolean isWordanalyser() {
        return wordanalyser;
    }

    public void setWordanalyser(boolean wordanalyser) {
        this.wordanalyser = wordanalyser;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof GameDto)) {
            return false;
        }
        GameDto c = (GameDto) o;
        return Objects.equals(id, c.id) &&
                Objects.equals(mySide, c.mySide) &&
                Objects.equals(otherSide, c.otherSide) &&
                Objects.equals(startDate, c.startDate) &&
                Objects.equals(myTurn, c.myTurn) &&
                Objects.equals(lang, c.lang);
    }


    @Override
    public String getEndpoint() {
        return endpoint;
    }
    /**
     * Endpoint to POST powers.
     * Is used to activate a power.
     */
    private ResourceWriteList<SideDto, PowerDto> powers = new ResourceWriteList<>(SideDto.class, PowerDto.class);

    public ResourceWriteList<GameDto, ChallengeDto> getChallenges() {
        return challenges;
    }

    public ResourceWriteList<SideDto, PowerDto> getPowers() {
        return powers;
    }

    public void setPowers(ResourceWriteList<SideDto, PowerDto> powers) {
        this.powers = powers;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        this.challenges.setEndpoint(endpoint + "/challenges");
        this.powers.setEndpoint(endpoint + "/powers");
    }

    @Override
    public Class<GameDto> getResourceType() {
        return resourceType;
    }
}
