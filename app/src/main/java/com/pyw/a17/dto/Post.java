package com.pyw.a17.dto;

import java.io.Serializable;

/**
 * Created by XNOTE on 2017-11-08.
 */

public class Post implements Serializable {

    public static final long serialNoUID = 010101010101;

    private int no;
    private String title;
    private String content;
    private String writeDate;
    private String writer;
    private String category;

    public Post() {

    }

    public Post(int no, String title, String content, String writeDate, String writer, String category) {
        this.no = no;
        this.title = title;
        this.content = content;
        this.writeDate = writeDate;
        this.writer = writer;
        this.category = category;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
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

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
