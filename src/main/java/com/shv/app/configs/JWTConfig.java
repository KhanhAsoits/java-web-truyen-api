package com.shv.app.configs;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Slf4j
public class JWTConfig extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String url = request.getServletPath();
            if (!url.equals("/login") && !url.equals("/api/v1/users/create")) {
                String token = getTokenFromHeader(request);
                if (token != null) {
                    log.info("Start jwt authenticate!");
                    Algorithm algorithm = Algorithm.HMAC256("shv".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String role = decodedJWT.getClaim("roles").asString();
                    Set<SimpleGrantedAuthority> authorities = new HashSet<>();
                    authorities.add(new SimpleGrantedAuthority(role));
                    log.info("get roles success");
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    log.info("login with {} and roles {}", decodedJWT.getSubject(), role);
                    response.setHeader("Permission","Has access token!");
                }else {
                    response.setHeader("Permission","No access token !");
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            response.getWriter().write(new Gson().toJson(new HashMap<String, String>().put("message", exception.getMessage())));
        }
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            log.info("token {}", authorization);
            return authorization.substring(7);
        }
        return null;
    }
}









