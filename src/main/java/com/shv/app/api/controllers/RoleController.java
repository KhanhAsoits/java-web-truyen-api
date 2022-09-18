package com.shv.app.api.controllers;

import com.shv.app.api.bases.interfaces.ICrudController;
import com.shv.app.comons.validators.controllers.HandleController;
import com.shv.app.dto.RoleDto;
import com.shv.app.services.role.RoleServiceIpm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
@Slf4j
public class RoleController extends HandleController<RoleDto> implements ICrudController<RoleDto, Long> {

    private final RoleServiceIpm roleServiceIpm;

    @PostMapping("/create")
    @Override
    public ResponseEntity<?> SaveEntity(@RequestBody @Valid RoleDto entity) {
        try {
            return ResponseEntity.ok().body(roleServiceIpm.save(entity));
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @PostMapping("/remove")
    @Override
    public ResponseEntity<?> RemoveEntity(Long entityId) {
        return null;
    }

    @Override
    public ResponseEntity<?> UpdateEntity(@RequestBody @Valid RoleDto entity) {
        try {
            return ResponseEntity.ok().body(roleServiceIpm.save(entity));
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @GetMapping("/{page}/{limit}")
    @Override
    public ResponseEntity<?> GetByPage(@PathVariable int page, @PathVariable int limit) {
        try {
            return ResponseEntity.ok().body(roleServiceIpm.findByPage(page, limit));
        } catch (Exception ex) {
            return this.InternalServerResponse(ex);
        }
    }

    @PostMapping("/create-list")
    @Override
    public ResponseEntity<?> SaveAll(List<RoleDto> list) {
        try {
            return ResponseEntity.ok(roleServiceIpm.saveAll(list));
        } catch (Exception exception) {
            return this.InternalServerResponse(exception);
        }
    }

    @GetMapping
    @Override
    public ResponseEntity<?> GetAll() {
        return this.ResultListResponse(roleServiceIpm.findAll());
    }

    @GetMapping("/search/{keyword}/{page}/{limit}")
    @Override
    public ResponseEntity<?> GetByKeyword(@PathVariable String keyword, @PathVariable int page, @PathVariable int limit) {
        try {
            return ResponseEntity.ok(roleServiceIpm.findByKeyWord(keyword, page, limit));
        } catch (Exception ex) {
            return this.InternalServerResponse(ex);
        }
    }
}
