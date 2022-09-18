package com.shv.app.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.UUID;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@SQLDelete(sql = "UPDATE chapter SET state = 0 where id = ?")
@FilterDef(name = "deletedChapterFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedChapterFilter", condition = "state = :isDeleted")
public class Chapter {
    public Chapter() {
    }
    @Column(unique = true)
    private String title;
    @Id
    private String id;

    private String content;

    private String slug;

    private int state = 1;

    private int numberOfChapter = 0;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getNumberOfChapter() {
        return numberOfChapter;
    }

    public void setNumberOfChapter(int numberOfChapter) {
        this.numberOfChapter = numberOfChapter;
    }

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;


}