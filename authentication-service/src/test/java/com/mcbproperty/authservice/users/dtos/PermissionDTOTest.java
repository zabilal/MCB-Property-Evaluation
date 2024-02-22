package com.mcbproperty.authservice.users.dtos;

import com.mcbproperty.authservice.entity.Permission;
import com.mcbproperty.authservice.pojo.response.PermissionDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PermissionDTOTest {

    @Test
    public void testPermissionDTOConstructor1() {
        PermissionDTO permissionDTO = new PermissionDTO();

        assertEquals(null, permissionDTO.getId());
        assertEquals(null, permissionDTO.getPermission());
    }

    @Test
    public void testPermissionDTOConstructor2() {
        Permission permission = new Permission(1L, "Browse website");

        PermissionDTO permissionDTO = new PermissionDTO(permission);

        assertEquals(permission.getId(), permissionDTO.getId());
        assertEquals(permission.getPermission(), permissionDTO.getPermission());
        assertTrue(permissionDTO.isEnabled());
    }

}
