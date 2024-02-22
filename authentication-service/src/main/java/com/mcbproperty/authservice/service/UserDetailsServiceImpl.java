package com.mcbproperty.authservice.service;

import com.mcbproperty.authservice.entity.Permission;
import com.mcbproperty.authservice.entity.Role;
import com.mcbproperty.authservice.entity.User;
import com.mcbproperty.authservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userAccount = userRepository.findByUsername(username);
        if (Objects.isNull(userAccount)) {
            throw new UsernameNotFoundException("User with username [" + username + "] not found in the system");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();

        for (Role role : userAccount.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
            for (Permission permission : role.getPermissions()) {
                authorities.add(new SimpleGrantedAuthority(permission.getPermission()));
            }
        }

        return new UserDetailsImpl(userAccount.getId(), userAccount.getUsername(), userAccount.getEmail(), userAccount.getPassword(), authorities);
    }

}