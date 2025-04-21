package com.uep.wap.eshop.remotech.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_role")
public class UserRole implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "authority", length = 50, nullable = false)
    private String authority;

    @OneToMany(mappedBy = "userRole")
    private List<User> users;

    // Default constructor
    public UserRole() {
    }

    // Constructor with all fields except id
    public UserRole(String authority, List<User> users) {
        setAuthority(authority); // Using setter to ensure ROLE_ prefix
        this.users = users;
    }

    // Getters
    public Long getId() {
        return id;
    }

    @Override
    public String getAuthority() {
        if (!authority.startsWith("ROLE_")) {
            return "ROLE_" + authority;
        }
        return authority;
    }

    public List<User> getUsers() {
        return users;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthority(String authority) {
        if (!authority.startsWith("ROLE_")) {
            this.authority = "ROLE_" + authority;
        } else {
            this.authority = authority;
        }
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(id, userRole.id) &&
               Objects.equals(authority, userRole.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authority);
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                '}';
    }
} 