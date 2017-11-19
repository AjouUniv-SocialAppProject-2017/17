package com.pyw.a17.dto;

/**
 * Created by XNOTE on 2017-05-20.
 */

public class CommentDTO {

    private int no;
    private String content;
    private String writer;
    private String writeDate;

    public CommentDTO() {

    }

    public CommentDTO(String content, int no, String writeDate, String writer) {
        this.content = content;
        this.no = no;
        this.writeDate = writeDate;
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
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
}
