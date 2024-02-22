package com.mcbproperty.authservice.repository;

import com.mcbproperty.authservice.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    @Query(value = "SELECT u FROM User u WHERE u.contact.email = :email")
    User findByEmail(@Param("email") String email);

    User findByUsername(String username);

    @Query(value = "select user_id from users_roles where roles_id = ?1", nativeQuery = true)
    List<Long> findByRoleId(Long id);
}
