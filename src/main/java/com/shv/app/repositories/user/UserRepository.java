package com.shv.app.repositories.user;

import com.shv.app.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(value = "select * from User join user_roles on user.id = user_roles.user_id join role on role.id = user_roles.role_id where username like %?1% or email like%?1%", nativeQuery = true)
    Page<User> findByKeyWord(String keyword, Pageable pageable);
}
