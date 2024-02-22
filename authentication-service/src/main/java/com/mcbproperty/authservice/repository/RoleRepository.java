package com.mcbproperty.authservice.repository;

import com.mcbproperty.authservice.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    @Query(value = "select count(*) from users_roles where role_id = ?1", nativeQuery = true)
    Long countRoleUsage(Long roleId);

//    Optional<Role> findByAuthority(String role);

    Optional<Role> findById(Long id);

    Optional<Role> findByRole(String roleStr);

    @Query(value = "select role_id from permissions_roles where permission_id = ?1", nativeQuery = true)
    Long findByPermission(Long permissionId);
}
