package com.mcbproperty.authservice.config;


import com.mcbproperty.authservice.repository.RoleRepository;
import com.mcbproperty.authservice.repository.UserRepository;
import com.mcbproperty.authservice.service.UserDetailsServiceImpl;
import com.mcbproperty.authservice.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private static final String JWT_SECRET = "$2a$10$8XT9BkaViS9c4TqSc6N8Bu/P1ICLXLZGN/pxDh/gngw6wEGgY0nGi";

//    @Autowired
    private UserRepository repository;

//    @Autowired
    private UserService userService;

//    @Autowired
    private RoleRepository roleRepository;

//    @Autowired
    private UserDetailsServiceImpl userDetailsService;

//    @Autowired
    private JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtil.validateJwtToken(jwt)) {
                String username = jwtUtil.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
//        ---------------------------------------------------------------------------------------------------
//        String authorizationHeader = request.getHeader("Authorization");
//        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String token = authorizationHeader.replace("Bearer ", "");
//        try {
//            Jws<Claims> claimsJws = Jwts.parserBuilder()
//                    .setSigningKey(Keys.hmacShaKeyFor(JWT_SECRET.getBytes()))
//                    .build()
//                    .parseClaimsJws(token);
//
//            Claims body = claimsJws.getBody();
//            String email = body.getSubject();
//            List<String> authorities = (List<String>) body.get("authorities");
//            Collection<Role> roles = new HashSet<>();
//
////            for (String role : authorities) {
////                Optional<Role> authority = roleRepository.findByAuthority(role);
////                if (authority.isPresent()) {
////                    roles.add(authority.get());
////                }
////            }
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                    email,
//                    null,
//                    roles
//            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } catch (JwtException e) {
//            throw new IllegalStateException(String.format("Token supplied cannot be trusted !"));
//        }
//
//        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}
