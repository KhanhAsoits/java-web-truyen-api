package com.shv.app.repositories.role;

import com.shv.app.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, String> {
    @Query(value = "select * from role where role_name like %?1%", nativeQuery = true)
    Page<Role> findByKeyword(String keyword, Pageable pageable);

    @Query(value = "select  * from role where role.role_name = ?1 ",nativeQuery = true)
    Role findByName(String name);
}
