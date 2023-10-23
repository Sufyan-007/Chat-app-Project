package com.ChatApp.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserAuthenticationProvider userAuthenticationProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("In filter");
        if(authHeader != null){
            String[] authToken= authHeader.split(" ");
            try {
                if (authToken.length == 2 && authToken[0].equals("Bearer")) {
                    SecurityContextHolder.getContext().setAuthentication(
                            userAuthenticationProvider.validateToken(authToken[1])
                    );
                } else {
                    SecurityContextHolder.getContext().setAuthentication(
                            userAuthenticationProvider.validateToken(authToken[0])
                    );
                }
            }catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request,response);
    }
}
