package com.shv.app.api.controllers;

import com.shv.app.api.bases.interfaces.ICrudController;
import com.shv.app.comons.validators.controllers.HandleController;
import com.shv.app.dto.BookDto;
import com.shv.app.services.book.BookServiceIpm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Slf4j

public class BookController extends HandleController<BookDto> implements ICrudController<BookDto,String> {

    private final BookServiceIpm bookServiceIpm;

    @Override
    @PostMapping("/create")
    public ResponseEntity<?> SaveEntity(@ModelAttribute @Valid BookDto id) {
        BookDto bookDto = bookServiceIpm.save(id);
        if (bookDto!=null){
            return this.ResultResponse(bookDto);
        }else {
            return this.BadRequest("Something wrong!");
        }
    }

    @Override
    public ResponseEntity<?> RemoveEntity(String entityId) {
        return null;
    }

    @Override
    public ResponseEntity<?> UpdateEntity(BookDto entity) {
        return null;
    }

    @Override
    public ResponseEntity<?> GetByPage(int page, int limit) {
        return null;
    }

    @Override
    public ResponseEntity<?> SaveAll(List<BookDto> list) {
        return null;
    }

    @Override
    public ResponseEntity<?> GetAll() {
        return null;
    }

    @Override
    public ResponseEntity<?> GetByKeyword(String keyword, int page, int limit) {
        return null;
    }
}
