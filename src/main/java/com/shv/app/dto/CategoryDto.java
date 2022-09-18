package com.shv.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shv.app.entities.Book;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

public class CategoryDto {
    private String id;

    @NotNull
    private String title;

    private String slug;


    @JsonIgnore
    Set<MultipartFile> files = new HashSet<>();

//    upload
    public Set<MultipartFile> getFiles() {
        return files;
    }


//    return
    private Set<String> thumbnails = new HashSet<>();

    public Set<String> getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(Set<String> thumbnails) {
        this.thumbnails = thumbnails;
    }

    public void setFiles(Set<MultipartFile> files) {
        this.files = files;
    }

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private Set<Book> books = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
