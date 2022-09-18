package com.shv.app.services.user;

import com.shv.app.dto.UserDto;
import com.shv.app.entities.Role;
import com.shv.app.entities.User;
import com.shv.app.repositories.user.UserRepository;
import com.shv.app.services.role.RoleServiceIpm;
import com.shv.app.utils.Json.PageBuilder;
import com.shv.app.utils.Mapper.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import javax.transaction.Transactional;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceIpm implements UserService {
    private final UserRepository userRepository;

    private final EntityManager entityManager;
    private final RoleServiceIpm roleServiceIpm;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public List<User> findAll() {
        try {
            log.info("get all users");
            Session session = entityManager.unwrap(Session.class);
            Filter filter = session.enableFilter("deletedUserFilter");
            filter.setParameter("isDeleted", true);
            List<User> users = userRepository.findAll();
            session.disableFilter("deletedUserFilter");
            return users;
        } catch (Exception exception) {
            return null;
        }
    }

    public User update(UserDto userDto) {
        log.info("update user!");
        int state = userDto.isState() == 1 ? 1 : 0;
        Optional<User> userToUpdate = userRepository.findByEmail(userDto.getEmail());
        if (userToUpdate.isPresent()) {
            userToUpdate.get().setEmail(userDto.getEmail());
            userToUpdate.get().setUsername(userDto.getUsername());
            userToUpdate.get().setState(state);
            userToUpdate.get().setPassword(new BCryptPasswordEncoder().encode(userToUpdate.get().getPassword()));
            log.info("update success!");
            return userRepository.save(userToUpdate.get());
        }
        return null;
    }

    @Override
    public User save(UserDto userDto) {
        try {
            log.info("Save new user!");
            Optional<User> unique = userRepository.findByEmail(userDto.getEmail());
            if (unique.isPresent()) {
                return null;
            }
            User user = new User(userDto.getUsername(), userDto.getEmail(), userDto.getPassword());
            String ScryptPassword = new BCryptPasswordEncoder().encode(user.getPassword());
            user.setPassword(ScryptPassword);
            User saved_user = userRepository.save(user);
            if (userDto.getRole_name() != null) {
                Role roleOfUser = roleServiceIpm.findByName(userDto.getRole_name());
                if (roleOfUser!=null){
                    saved_user.addRoleToUser(roleOfUser);
                }else {
                    Role defaultRole = roleServiceIpm.findByName("role_user");
                    saved_user.addRoleToUser(defaultRole);
                }
                return userRepository.save(saved_user);
            }
            return saved_user;

        } catch (Exception exception) {
            log.info("error : {}",exception.getMessage());
            return null;
        }
    }

    public Page<User> findByPage(int page, int limit) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedUserFilter");
        filter.setParameter("isDeleted", true);
        Pageable pageable = PageBuilder.createReq(page, limit);
        Page<User> users = userRepository.findAll(pageable);
        session.disableFilter("deletedUserFilter");
        return users;
    }

    public List<User> saveAll(List<UserDto> list) {
        ObjectMapper<UserDto, User> mapper = new ObjectMapper<>();
        List<User> users = list.stream().map(userDto -> mapper.Map(userDto, User.class)).toList();
        return userRepository.saveAll(users);
    }

    public Page<User> findByKeyword(String keyword, int page, int limit) {
        Pageable pageable = PageBuilder.createReq(page, limit);
        return userRepository.findByKeyWord(keyword, pageable);
    }


    public User remove(Long entityId) {
        Optional<User> user = userRepository.findById(entityId);
        if (user.isPresent()) {
            userRepository.deleteById(entityId);
            return user.get();
        }
        return null;
    }
}
