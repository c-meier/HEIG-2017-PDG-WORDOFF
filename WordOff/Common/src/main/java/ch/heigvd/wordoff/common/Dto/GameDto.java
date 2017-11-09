package ch.heigvd.wordoff.common.Dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
import java.util.Objects;

/**
 * Project : WordOff
 * Date : 10.10.17
 */
public class GameDto {
    private Long id;

    private SideDto mySide;

    @JsonSerialize(typing = JsonSerialize.Typing.STATIC)
    private OtherSideDto otherSide;

    private Date startDate;

    private String lang;

    private boolean myTurn;

    /* TODO -> create GameSummaryDto */

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

    public OtherSideDto getOtherSide() {
        return otherSide;
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

    public Long getId() {
        return id;
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
}
