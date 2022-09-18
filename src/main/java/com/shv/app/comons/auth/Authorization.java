package com.shv.app.comons.auth;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface Authorization {

    public String role_name() default "";

}
