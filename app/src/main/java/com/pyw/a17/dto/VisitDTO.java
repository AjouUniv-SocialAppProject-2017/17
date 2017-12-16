package com.pyw.a17.dto;

/**
 * Created by XNOTE on 2017-12-16.
 */

public class VisitDTO {

    String title;
    String content;
    String username;
    String who;

    public VisitDTO() {

    }

    public VisitDTO(String title, String content, String username, String who) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.who = who;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
