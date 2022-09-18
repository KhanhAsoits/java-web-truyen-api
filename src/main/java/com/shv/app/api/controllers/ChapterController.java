package com.shv.app.api.controllers;

import com.shv.app.api.bases.interfaces.ICrudController;
import com.shv.app.comons.validators.controllers.HandleController;
import com.shv.app.dto.ChapterDto;
import com.shv.app.services.chapter.ChapterService;
import com.shv.app.services.chapter.ChapterServiceIpm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/chapters")
@Slf4j
@RequiredArgsConstructor
public class ChapterController extends HandleController<ChapterDto> implements ICrudController<ChapterDto,String> {

    private final ChapterServiceIpm chapterServiceIpm;


    @Override
    @PostMapping("/create")
    public ResponseEntity<?> SaveEntity(@RequestBody @Valid ChapterDto id) {
        try {
            return this.ResultResponse(chapterServiceIpm.save(id));
        }catch (Exception exception){
            return this.InternalServerResponse(exception);
        }
    }

    @Override
    public ResponseEntity<?> RemoveEntity(String entityId) {
        return null;
    }

    @Override
    public ResponseEntity<?> UpdateEntity(ChapterDto entity) {
        return null;
    }

    @Override
    public ResponseEntity<?> GetByPage(int page, int limit) {
        return null;
    }

    @Override
    public ResponseEntity<?> SaveAll(List<ChapterDto> list) {
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
