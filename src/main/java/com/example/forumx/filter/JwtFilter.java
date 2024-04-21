package com.example.forumx.filter;

import com.example.forumx.service.UserService;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Value("${jwt.secretkey:forumx}")
    private String secretKey;


    private final UserService userService;

    public JwtFilter(UserService userService){
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = getJwtFromRequest(request);
            if(StringUtils.hasText(jwt)){
                String username = getUsername(jwt);
                String role = getRole(jwt);
                String name = getName(jwt);

                userService.createOrUpdateUser(username, name);

                List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
                if(StringUtils.hasText(role) && StringUtils.hasText(username)){
                    authorityList.add(new SimpleGrantedAuthority(role));
                }else{
                    throw new AuthenticationCredentialsNotFoundException("Invalid access token");
                }
                UserDetails userDetails = new User(username, "", authorityList);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (AuthenticationException ex){
            log.info("Could not set user authentication in security context", ex);
            throw new com.example.forumx.exception.AuthenticationException("Authentication exception");
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        log.info(bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getUsername(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return null;
    }

    public String getRole(String token){
        try {
            return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("ROLE");
        }catch (SignatureException ex) {
                logger.error("Invalid JWT signature");
            } catch (MalformedJwtException ex) {
                logger.error("Invalid JWT token");
            } catch (ExpiredJwtException ex) {
                logger.error("Expired JWT token");
            } catch (UnsupportedJwtException ex) {
                logger.error("Unsupported JWT token");
            } catch (IllegalArgumentException ex) {
                logger.error("JWT claims string is empty.");
            }
        return null;
    }

    public String getName(String token){
        try {
            return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("NAME");
        }catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return null;
    }
}
