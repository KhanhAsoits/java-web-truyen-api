package com.shv.app.services.user;

import com.shv.app.dto.UserDto;
import com.shv.app.entities.User;

public interface UserService {
    User findByEmail(String email);

    User save(UserDto user);

}
