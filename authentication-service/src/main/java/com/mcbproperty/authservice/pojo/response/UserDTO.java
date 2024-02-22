package com.mcbproperty.authservice.pojo.response;

import com.mcbproperty.authservice.entity.Permission;
import com.mcbproperty.authservice.entity.Role;
import com.mcbproperty.authservice.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO implements Serializable {

    private Long id;
    private String username;
    private String lastName;
    private String firstName;

    private LocalDateTime creationDt;
    private LocalDateTime updatedDt;
    private LocalDateTime loginDt;

    private boolean secured;

    private boolean enabled;
    // permissions and roles list
    private List<String> roles;
    private List<String> permissions;

    private String userId;

    public UserDTO() {
        // empty constructor
        roles = new ArrayList<>();
        permissions = new ArrayList<>();
    }

    public UserDTO(User user) {
        if (user != null) {
            this.userId = user.getUserId();
            this.id = user.getId();
            this.username = user.getUsername();
            this.lastName = user.getLastName();
            this.firstName = user.getFirstName();

            this.enabled = user.isEnabled();

            this.loginDt = user.getLoginDt();

            this.secured = user.isSecured();

            // Because the permissions can be associated to more than one roles i'm creating two String arrays
            // with the distinct keys of roles and permissions.
            roles = new ArrayList<>();
            permissions = new ArrayList<>();

            for (Role role : user.getRoles()) {
                roles.add(role.getRole());
                for (Permission p : role.getPermissions()) {
                    String key = p.getPermission();
                    if ((!permissions.contains(key)) && (p.isEnabled())) {
                        // add the permission only if enabled
                        permissions.add(key);
                    }
                }
            }

        }
    }

}
