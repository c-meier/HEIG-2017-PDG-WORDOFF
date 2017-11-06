package ch.heigvd.wordoff.common.Dto;

import java.util.Date;

/**
 * Project : WordOff
 * Date : 10.10.17
 */
public class GameDto {
    private Long id;

    private SideDto side1;

    private SideDto side2;

    private Date startDate;

    private String lang;

    private PlayerDto currPlayer;

    /* TODO -> create GameSummaryDto */

    public GameDto(Long id, SideDto side1, SideDto side2, PlayerDto currPlayer, String lang, Date startDate) {
        this.id = id;
        this.side1 = side1;
        this.side2 = side2;
        this.lang = lang;
        this.startDate = startDate;
        this.currPlayer = currPlayer;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public SideDto getSide1() {
        return side1;
    }

    public SideDto getSide2() {
        return side2;
    }

    public PlayerDto getCurrPlayer() {
        return currPlayer;
    }

    public void setCurrPlayer(PlayerDto currPlayer) {
        this.currPlayer = currPlayer;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Long getId() {
        return id;
    }
}
