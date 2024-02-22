package com.mcbproperty.authservice.controllers;

import com.mcbproperty.authservice.entity.Permission;
import com.mcbproperty.authservice.entity.Role;
import com.mcbproperty.authservice.pojo.response.PermissionDTO;
import com.mcbproperty.authservice.pojo.response.RoleDTO;
import com.mcbproperty.authservice.service.EncryptionService;
import com.mcbproperty.authservice.service.PermissionService;
import com.mcbproperty.authservice.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/rbac")
@AllArgsConstructor
public class RBACRestController {

    private RoleService roleService;

    private PermissionService permissionService;

    // roles
    @GetMapping("/roles")
    public ResponseEntity<List<RoleDTO>> getRolePresentationList() {
        Iterable<Role> roleList = roleService.getRoleList();
        ArrayList<RoleDTO> list = new ArrayList<>();
        roleList.forEach(e -> list.add(new RoleDTO(e)));
        return ResponseEntity.ok(list);
    }

    @PostMapping("/roles")
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) {
        return new ResponseEntity<>(new RoleDTO(roleService.createRole(roleDTO)), null, HttpStatus.CREATED);
    }

    @GetMapping("/roles/{roleId}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable("roleId") Long roleId) {
        return new ResponseEntity<>(
                new RoleDTO(roleService.getRoleById(roleId)), HttpStatus.OK);
    }

    @DeleteMapping("/roles/{roleId}")
    public ResponseEntity<?> deleteRoleById(@PathVariable("organizationCraterId")String organizationCraterId,
                                            @PathVariable("roleId") Long roleId) {
        roleService.deleteRole(organizationCraterId, roleId);
        return ResponseEntity.noContent().build();
    }

    // retrieve the permission's list
    @GetMapping("/permissions/")
    public ResponseEntity<List<PermissionDTO>> getPermissionPresentationList(@PathVariable("organizationCraterId")String organizationCraterId) {
        Iterable<Permission> permissionList = permissionService.getPermissionList(organizationCraterId);
        ArrayList<PermissionDTO> list = new ArrayList<>();
        permissionList.forEach(e -> list.add(new PermissionDTO(e)));
        return ResponseEntity.ok(list);
    }

    // permissions
    @GetMapping("/permissions/{permissionKey}")
    public ResponseEntity<PermissionDTO> getPermissionByKey(@PathVariable("organizationCraterId")String organizationCraterId,
                                                            @PathVariable("permissionKey") String permissionKey) {
        PermissionDTO permissionDTO = new PermissionDTO(permissionService.getPermissionByKey(organizationCraterId, permissionKey));
        return ResponseEntity.ok(permissionDTO);
    }

    @PostMapping("/permissions")
    public ResponseEntity<PermissionDTO> createPermission(@RequestBody PermissionDTO permissionDTO) {
        return new ResponseEntity<>(new PermissionDTO(permissionService.createPermission(permissionDTO)),
                HttpStatus.CREATED);
    }

    @PutMapping("/permissions")
    public ResponseEntity<PermissionDTO> updatePermission(@RequestBody PermissionDTO permissionDTO) {
        return new ResponseEntity<>(new PermissionDTO(permissionService.updatePermission(permissionDTO)),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/permissions/{permissionKey}")
    public ResponseEntity<?> deletePermissionByKey(@PathVariable("organizationCraterId")String organizationCraterId,
                                                   @PathVariable("permissionKey") String permissionKey) {
        permissionService.deletePermissionByKey(organizationCraterId, permissionKey);
        return ResponseEntity.noContent().build();
    }

    // add or remove a Permission on a Role

    @PostMapping("/roles/{roleId}/permissions/{permissionKey}")
    public ResponseEntity<RoleDTO> addPermissionOnRole(
            @PathVariable("organizationCraterId")String organizationCraterId,
            @PathVariable("roleId") Long roleId,
            @PathVariable("permissionKey") String permissionKey) {
        return new ResponseEntity<>(new RoleDTO(roleService.addPermissionOnRole(organizationCraterId, roleId, permissionKey)), null,
                HttpStatus.CREATED);
    }

    @DeleteMapping("/roles/{roleId}/permissions/{permissionKey}")
    public ResponseEntity<RoleDTO> removePermissionOnRole(
            @PathVariable("organizationCraterId")String organizationCraterId,
            @PathVariable("roleId") Long roleId,
            @PathVariable("permissionKey") String permissionKey) {
        return new ResponseEntity<>(new RoleDTO(roleService.removePermissionOnRole(organizationCraterId, roleId, permissionKey)), null,
                HttpStatus.OK);
    }

    // salt generation
    @GetMapping("/salt")
    public ResponseEntity<String> generateSalt() {
        return new ResponseEntity<String>(EncryptionService.generateSalt(32), HttpStatus.CREATED);
    }

}
