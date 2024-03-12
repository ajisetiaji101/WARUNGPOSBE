package com.warungposbespring.warungposbe.utils;

import com.warungposbespring.warungposbe.config.AppAuthentication;
import com.warungposbespring.warungposbe.dao.UserDao;
import com.warungposbespring.warungposbe.dto.AuthUserResponse;
import com.warungposbespring.warungposbe.dto.UserForJwtResponse;
import com.warungposbespring.warungposbe.enums.AppSource;
import com.warungposbespring.warungposbe.service.UserService;
import com.warungposbespring.warungposbe.service.impl.CustomerUserDetailsService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final CustomerUserDetailsService customUserDetailsService;

    private final UserDao userDao;

    public JwtAuthFilter(JwtUtil jwtUtil, CustomerUserDetailsService customUserDetailsService, UserDao userDao) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
        this.userDao = userDao;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = null;
        String token = jwtUtil.resolveToken(request);

        if (token != null) {

            Claims bodyToken = jwtUtil.resolveClaims(request);

            username = bodyToken.getSubject();

            if (username == null) {
                response.sendError(403, "Username dan Password tidak ditemukan");
            }

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            if (userDetails != null) {
//                UsernamePasswordAuthenticationToken authentication =
//                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());

                int tokenAuth = userDao.getSession(token);
                if(tokenAuth == 0){
                    throw new AppException("Anda telah logout");
                }

                AuthUserResponse dataAuth = userDao.findByEmailOrUsername(username);

                UserForJwtResponse dataUser = dataAuth.getUser();

                AppAuthentication appAuthentication =new AppAuthentication(dataUser, AppSource.FO);

                log.info("authenticated user with email :{}", username);
                SecurityContextHolder.getContext().setAuthentication(appAuthentication);
                filterChain.doFilter(request, response);
                return;
            }

        }
        filterChain.doFilter(request, response);
    }
}
