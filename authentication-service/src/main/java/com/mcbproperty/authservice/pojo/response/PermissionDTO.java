package com.mcbproperty.authservice.pojo.response;

import com.mcbproperty.authservice.entity.Permission;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PermissionDTO implements java.io.Serializable {

    private Long id;
    private String permission;
    private boolean enabled;
    private String note;
    private String organizationCraterId;

    public PermissionDTO(Permission permission) {
        this.id = permission.getId();
        this.permission = permission.getPermission();
        this.enabled = permission.isEnabled();
        this.note = permission.getNote();
        this.organizationCraterId = permission.getOrganizationCraterId();
    }

}
