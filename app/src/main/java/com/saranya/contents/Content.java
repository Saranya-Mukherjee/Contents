package com.saranya.contents;

public class Content {
    String teacher,topic, pg_start,pg_end,copy;

    public Content(String teacher, String topic, String pg_start, String pg_end, String copy) {
        this.teacher = teacher;
        this.topic = topic;
        this.pg_start = pg_start;
        this.pg_end = pg_end;
        this.copy = copy;
    }

    public Content() {
        this.teacher = "";
        this.topic = "";
        this.pg_start = "";
        this.pg_end = "";
        this.copy = "";
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPg_start() {
        return pg_start;
    }

    public void setPg_start(String pg_start) {
        this.pg_start = pg_start;
    }

    public String getPg_end() {
        return pg_end;
    }

    public void setPg_end(String pg_end) {
        this.pg_end = pg_end;
    }

    public String getCopy() {
        return copy;
    }

    public void setCopy(String copy) {
        this.copy = copy;
    }
}
