package com.shv.app.services.role;

import com.shv.app.dto.RoleDto;
import com.shv.app.entities.Role;
import com.shv.app.repositories.role.RoleRepository;
import com.shv.app.utils.Json.PageBuilder;
import com.shv.app.utils.Mapper.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceIpm implements RoleService {



    private final RoleRepository roleRepository;

    public Role findById(String id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public RoleDto save(RoleDto entity) {
        Role role = new Role(entity.getRole_name());
        Role saved_role = roleRepository.save(role);
        return new RoleDto(saved_role.getId().toString(), saved_role.getRole_name());
    }

    @Override
    public RoleDto update(RoleDto entity) {
        return null;
    }

    @Override
    public void removeById(String s) {

    }

    public Role findByName(String name){
        return roleRepository.findByName(name);
    }

    @Override
    public RoleDto remove(RoleDto entity) {
        return null;
    }

    @Override
    public List<RoleDto> findAll() {
        try {
            List<Role> roles = roleRepository.findAll();
            return roles.stream().map(role -> new RoleDto(role.getId(), role.getRole_name())).toList();
        }catch (Exception ex){
            log.info("error : {}",ex.getMessage());
            return null;
        }

    }

    @Override
    public Page<RoleDto> findByPage(int page, int limit) {
        Pageable pageable = PageBuilder.createReq(page, limit);
        Page<Role> roles = roleRepository.findAll(pageable);
        ObjectMapper<Role, RoleDto> mapper = new ObjectMapper<>();
        return new PageImpl<>(roles.getContent().stream().map(role -> mapper.Map(role, RoleDto.class)).toList());
    }

    @Override
    public List<RoleDto> findByKeyWord(String keyword) {
        return null;
    }

    @Override
    public Page<RoleDto> search(String query, int page, int limit) {
        return null;
    }

    @Override
    public Role findById(Long id) {
        return null;
    }

    @Override
    public Page<RoleDto> findByKeyWord(String keyword, int page, int limit) {
        Pageable pageable = PageBuilder.createReq(page, limit);
        ObjectMapper<Role, RoleDto> mapper = new ObjectMapper<>();
        Page<Role> roles = roleRepository.findByKeyword(keyword, pageable);
        return new PageImpl<RoleDto>(roles.getContent().stream().map(role -> mapper.Map(role, RoleDto.class)).toList());
    }


    public List<RoleDto> saveAll(List<RoleDto> list) {
        ObjectMapper<RoleDto, Role> mapper = new ObjectMapper<>();
        ObjectMapper<Role, RoleDto> roleRoleDtoObjectMapper = new ObjectMapper<>();
        List<Role> roles = list.stream().map(roleDto -> mapper.Map(roleDto, Role.class)).toList();
        List<Role> roleList = roleRepository.saveAll(roles);
        return roleList.stream().map(role -> roleRoleDtoObjectMapper.Map(role, RoleDto.class)).toList();
    }
}
