package com.videostream.app.security;

import com.videostream.app.service.CustomUserDetailService;
import com.videostream.app.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
To understand what this class does and why i have implemented certain methods
pls refer to ->
@link https://github.com/Enforc3rr/JWT-Spring-Boot/blob/master/src/main/java/com/jwtexample/jwt/security/JwtAuthenticationFilter.java

AS , This repo contains comments which explains why have i implemented and called certain methods.
*/
@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String requestTokenHeader = httpServletRequest.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")){
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = this.jwtUtil.extractUsername(jwtToken);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        UserDetails userDetails = this.customUserDetailService.loadUserByUsername(username);

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =  new UsernamePasswordAuthenticationToken(
                    userDetails , null , userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }else{
            httpServletResponse.sendError(400 , "Invalid Token");
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
