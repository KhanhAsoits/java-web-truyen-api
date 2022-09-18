package com.shv.app;

import com.shv.app.dto.RoleDto;
import com.shv.app.services.role.RoleService;
import com.shv.app.services.role.RoleServiceIpm;
import com.shv.app.services.user.UserService;
import com.shv.app.services.user.UserServiceIpm;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(UserServiceIpm userServiceIpm, RoleServiceIpm roleServiceIpm) {
        return args -> {
            roleServiceIpm.save(new RoleDto("role_user"));
            roleServiceIpm.save(new RoleDto("role_admin"));
            roleServiceIpm.save(new RoleDto("role_guest"));
        };
    }
}
