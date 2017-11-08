package com.pyw.a17.dto;

/**
 * Created by XNOTE on 2017-11-08.
 */

public class Post {

    private String title;
    private String content;
    private String date;
    private String writer;
    private String reply;

    public Post() {

    }

    public Post(String title, String content, String date, String writer, String reply) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.writer = writer;
        this.reply = reply;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
