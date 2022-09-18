package com.shv.app.services.user;

import com.shv.app.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserDetailServiceIpm implements UserDetailsService {
    private final UserServiceIpm userServiceIpm;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userServiceIpm.findByEmail(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found!");
            }
            log.info("Load user success! user  : {}", user.getUsername());
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();

            user.getRoles().stream().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getRole_name()));
            });
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
        } catch (Exception ex) {
            return null;
        }
    }
}








