package ch.heigvd.wordoff.common.Dto;

public class AnswerDto {

    private Long sideId;

    private Short num;

    private String word;

    private int score;

    public AnswerDto() {
    }

    public AnswerDto(SideDto side, Short num, String word, int score) {
        this.sideId = side.getId();
        this.num = num;
        this.word = word;
        this.score = score;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Long getSideId() {
        return sideId;
    }

    public void setSideId(Long sideId) {
        this.sideId = sideId;
    }

    public Short getNum() {
        return num;
    }

    public void setNum(Short num) {
        this.num = num;
    }
}
