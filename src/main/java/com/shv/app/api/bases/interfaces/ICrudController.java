package com.shv.app.api.bases.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

public interface ICrudController<E, TypeId> {

    ResponseEntity<?> SaveEntity(@RequestBody @Valid E id);

    ResponseEntity<?> RemoveEntity(@PathVariable @Valid TypeId entityId);

    ResponseEntity<?> UpdateEntity(@RequestBody @Valid E entity);

    ResponseEntity<?> GetByPage(@PathVariable int page, @PathVariable int limit);

    ResponseEntity<?> SaveAll(@RequestBody @Valid List<E> list);

    ResponseEntity<?> GetAll();
    ResponseEntity<?> GetByKeyword(@PathVariable String keyword, @PathVariable int page, @PathVariable int limit);
}
