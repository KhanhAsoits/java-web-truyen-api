package com.shv.app.comons.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class AuthorizationProcessor {
    public AuthorizationProcessor(Object object) throws Exception {


    }

    public Boolean CheckIfValid(Object object) throws Exception {
        if (CheckIsNull(object)) {
            Class<?> clazz = object.getClass();
            if (!clazz.isAnnotationPresent(Authorization.class)) {
                throw new Exception(String.format("Class : %s not Authorization class!", clazz.getSimpleName()));
            }
            return true;
        }
        return false;
    }

    public boolean CheckIsNull(Object object) throws Exception {
        if (Objects.isNull(object)) {
            throw new Exception("Object null!");
        }
        return true;
    }
}
