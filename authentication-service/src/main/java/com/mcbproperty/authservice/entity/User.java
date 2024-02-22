package com.mcbproperty.authservice.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;


import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
@Getter
@Setter
@ToString
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="id")
    private Long id;

    @Column(name="userId")
    private String userId;

    @Column(name="username", nullable = false)
    private String username;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="firstname", nullable = false)
    private String firstName;

    @Column(name="othername")
    private String otherName;

    @Column(name="surname", nullable = false)
    private String lastName;

    @Column(name="enabled")
    private boolean enabled;

    @Basic
    private java.time.LocalDateTime loginDt;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "users_roles",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name="secured")
    private boolean secured;

}
