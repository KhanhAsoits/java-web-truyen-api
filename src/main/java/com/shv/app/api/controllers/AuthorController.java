package com.shv.app.api.controllers;

import com.shv.app.api.bases.interfaces.ICrudController;
import com.shv.app.comons.validators.controllers.HandleController;
import com.shv.app.dto.AuthorDto;
import com.shv.app.services.author.AuthorService;
import com.shv.app.services.author.AuthorServiceIpm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/authors")
@RequiredArgsConstructor
@Slf4j
public class AuthorController extends HandleController<AuthorDto> implements ICrudController<AuthorDto,String> {

    private final AuthorServiceIpm authorServiceIpm;
    @Override
    @PostMapping("/create")
    public ResponseEntity<?> SaveEntity(AuthorDto id) {
      try {

          AuthorDto authorDto =  authorServiceIpm.save(id);
          if (authorDto == null){
              return this.BadRequest("No user found!");
          }
          return this.ResultResponse(authorDto);
      }catch (Exception ex){
          return this.InternalServerResponse(ex);
      }
    }

    @Override
    public ResponseEntity<?> RemoveEntity(String entityId) {
        return null;
    }

    @Override
    public ResponseEntity<?> UpdateEntity(AuthorDto entity) {
        return null;
    }

    @Override
    public ResponseEntity<?> GetByPage(int page, int limit) {
        return null;
    }

    @Override
    public ResponseEntity<?> SaveAll(List<AuthorDto> list) {
        return null;
    }

    @Override
    @GetMapping
    public ResponseEntity<?> GetAll() {
        List<AuthorDto> authorDtos = authorServiceIpm.findAll();
        if (authorDtos.size() > 0 ){
            return this.ResultListResponse(authorDtos);
        }
        return this.BadRequest("Something Wrong!");
    }

    @Override
    public ResponseEntity<?> GetByKeyword(String keyword, int page, int limit) {
        return null;
    }
}
