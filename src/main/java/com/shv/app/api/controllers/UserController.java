package com.shv.app.api.controllers;


import com.shv.app.api.bases.interfaces.ICrudController;
import com.shv.app.comons.validators.controllers.HandleController;
import com.shv.app.dto.UserDto;
import com.shv.app.entities.User;
import com.shv.app.services.user.UserService;
import com.shv.app.services.user.UserServiceIpm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Path;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController extends HandleController<User> implements ICrudController<UserDto, Long> {

    private final UserServiceIpm userServiceIpm;

    @GetMapping("/get-by-email/{email}")
    public ResponseEntity<?> GetUserById(@PathVariable String email) {
        try {
            User user = userServiceIpm.findByEmail(email);
            if (user != null) {
                return this.ResultResponse(user);
            }
            return ResponseEntity.badRequest().body("No user found by email!");
        } catch (Exception e) {
            return this.InternalServerResponse(e);
        }
    }


    @Override
    @PostMapping(path = "/create")
    public ResponseEntity<?> SaveEntity(@RequestBody @Valid UserDto entity) {
        User saved_user = userServiceIpm.save(entity);
        if (saved_user == null) {
            return ResponseEntity.badRequest().body("Email has taken!");
        }
        return ResponseEntity.ok().body(saved_user);
    }

    @PostMapping("/remove/{id}")
    @Override
    public ResponseEntity<?> RemoveEntity(@PathVariable Long id) {
        try {
            User user = userServiceIpm.remove(id);
            if (user != null) {
                return this.ResultResponse(user);
            }
            return ResponseEntity.badRequest().body("No user found!");
        } catch (Exception ex) {
            return this.InternalServerResponse(ex);
        }
    }

    @PostMapping("/update")
    @Override
    public ResponseEntity<?> UpdateEntity(@RequestBody @Valid UserDto entity) {
        try {
            User user = userServiceIpm.update(entity);
            if (user != null) {
                return this.ResultResponse(user);
            }
            return ResponseEntity.badRequest().body("No user found!");
        } catch (Exception exception) {
            return this.InternalServerResponse(exception);
        }
    }

    @GetMapping("/{page}/{limit}")
    @Override
    public ResponseEntity<?> GetByPage(@PathVariable int page, @PathVariable int limit) {
        try {
            return ResponseEntity.ok(userServiceIpm.findByPage(page, limit));
        } catch (Exception e) {
            return this.InternalServerResponse(e);
        }
    }

    @PostMapping("/create-list")
    @Override
    public ResponseEntity<?> SaveAll(List<UserDto> list) {
        try {
            return this.ResultListResponse(userServiceIpm.saveAll(list));
        } catch (Exception ex) {
            return this.InternalServerResponse(ex);
        }
    }

    @Override
    @GetMapping(path = "/list")
    public ResponseEntity<?> GetAll() {
        return ResponseEntity.ok().body(userServiceIpm.findAll());
    }

    @GetMapping("/search/{keyword}/{page}/{limit}")
    @Override
    public ResponseEntity<?> GetByKeyword(@PathVariable String keyword, @PathVariable int page, @PathVariable int limit) {
        try {
            return ResponseEntity.ok(userServiceIpm.findByKeyword(keyword, page, limit));
        } catch (Exception exception) {
            return this.InternalServerResponse(exception);
        }
    }

}
