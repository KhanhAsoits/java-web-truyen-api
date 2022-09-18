package com.shv.app.services.role;

import com.shv.app.dto.RoleDto;
import com.shv.app.dto.UserDto;
import com.shv.app.entities.Role;
import com.shv.app.services.base.ICrudService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService extends ICrudService<RoleDto, String > {
    Role findById(Long id);

    Page<RoleDto> findByKeyWord(String keyword, int page, int limit);
}
