package com.uep.wap.eshop.remotech.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Entity
@Table(name = "user_role")
public class UserRole implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "authority", length = 50, nullable = false)
    private String authority;

    @OneToOne(mappedBy = "userRole")
    private List<User> users;

    @Override
    public String getAuthority() {
        // Ensure the authority is properly prefixed for Spring Security
        if (!authority.startsWith("ROLE_")) {
            return "ROLE_" + authority;
        }
        return authority;
    }

    public void setAuthority(String authority) {
        // Ensure the authority is properly prefixed when setting
        if (!authority.startsWith("ROLE_")) {
            this.authority = "ROLE_" + authority;
        } else {
            this.authority = authority;
        }
    }

    // Custom toString to prevent infinite recursion with User entity
    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                '}';
    }
} 