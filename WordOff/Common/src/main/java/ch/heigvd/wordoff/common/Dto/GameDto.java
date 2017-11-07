package ch.heigvd.wordoff.common.Dto;

import java.util.Date;

/**
 * Project : WordOff
 * Date : 10.10.17
 */
public class GameDto {
    private Long id;

    private SideDto mySide;

    private OtherSideDto otherSide;

    private Date startDate;

    private String lang;

    private boolean myTurn;

    /* TODO -> create GameSummaryDto */

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
}
