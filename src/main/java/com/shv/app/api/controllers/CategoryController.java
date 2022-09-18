package com.shv.app.api.controllers;

import com.shv.app.api.bases.interfaces.ICrudController;
import com.shv.app.comons.validators.controllers.HandleController;
import com.shv.app.dto.CategoryDto;
import com.shv.app.services.category.CategoryServiceIpm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/categories")
@Slf4j
public class CategoryController extends HandleController<CategoryDto> implements ICrudController<CategoryDto,Long> {

    private final CategoryServiceIpm categoryServiceIpm;

    @PostMapping(path = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('role_admin')")
    @Override
    public ResponseEntity<?> SaveEntity(@ModelAttribute @Valid CategoryDto id) {
        try{
            return this.ResultResponse(categoryServiceIpm.save(id));
        }catch (Exception exception){
            return this.InternalServerResponse(exception);
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> FindById(@PathVariable String id){
        return ResponseEntity.ok(categoryServiceIpm.findById(id));
    }

    @Override
    public ResponseEntity<?> RemoveEntity(Long entityId) {
        return null;
    }

    @Override
    public ResponseEntity<?> UpdateEntity(CategoryDto entity) {
        return null;
    }

    @Override
    public ResponseEntity<?> GetByPage(int page, int limit) {
        return null;
    }

    @Override
    public ResponseEntity<?> SaveAll(List<CategoryDto> list) {
        return null;
    }

    @Override
    @GetMapping
    public ResponseEntity<?> GetAll() {
        try {
            return this.ResultListResponse(categoryServiceIpm.findAll());
        }catch (Exception ex){
            return this.InternalServerResponse(ex);
        }
    }

    @Override
    public ResponseEntity<?> GetByKeyword(String keyword, int page, int limit) {
        return null;
    }
}
