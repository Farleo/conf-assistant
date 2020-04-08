package lms.itcluster.confassistant.dto;

public class EditTopicDTO {
    private Long topicId;
    private String name;
    private int day;
    private int month;
    private int year;
    private int beginHour;
    private int beginMinuets;
    private int finishHour;
    private int finishMinuets;
    private String info;

    public EditTopicDTO() {
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getBeginHour() {
        return beginHour;
    }

    public void setBeginHour(int beginHour) {
        this.beginHour = beginHour;
    }

    public int getBeginMinuets() {
        return beginMinuets;
    }

    public void setBeginMinuets(int beginMinuets) {
        this.beginMinuets = beginMinuets;
    }

    public int getFinishHour() {
        return finishHour;
    }

    public void setFinishHour(int finishHour) {
        this.finishHour = finishHour;
    }

    public int getFinishMinuets() {
        return finishMinuets;
    }

    public void setFinishMinuets(int finishMinuets) {
        this.finishMinuets = finishMinuets;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
