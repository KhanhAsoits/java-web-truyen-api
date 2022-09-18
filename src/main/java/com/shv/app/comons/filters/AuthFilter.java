package com.shv.app.comons.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import com.shv.app.entities.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

class AuthInfo {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

@Slf4j
public class AuthFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public AuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            String path = request.getServletPath();
            log.info("start authenticate! ");
            if (path.equals("/login")) {
                BufferedReader reader = null;
                try {
                    reader = request.getReader();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                AuthInfo authInfo = new Gson().fromJson(reader, AuthInfo.class);
                log.info("Login with email : {} and password : {}", authInfo.getEmail(), authInfo.getPassword());
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authInfo.getEmail(), authInfo.getPassword());
                return authenticationManager.authenticate(token);
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetails user = (UserDetails) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("shv".getBytes());
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 30 * 1000000)).
                withIssuer(request.getRequestURL().toString()).sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000000)).
                withIssuer(request.getRequestURL().toString()).sign(algorithm);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.getWriter().write(new Gson().toJson(tokens));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.getWriter().write(new Gson().toJson(new HashMap<>().put("message", "Email or password incorrect!")));
    }
}
