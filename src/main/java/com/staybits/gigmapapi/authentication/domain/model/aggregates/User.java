package com.staybits.gigmapapi.authentication.domain.model.aggregates;

import com.staybits.gigmapapi.authentication.domain.model.valueobjects.Role;
import com.staybits.gigmapapi.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends AuditableAbstractAggregateRoot<User> {
    
    @NotBlank
    @Email
    @Size(max = 100)
    @Column(unique = true, nullable = false)
    private String email;
    
    @NotBlank
    @Size(max = 50)
    @Column(unique = true, nullable = false)
    private String username;
    
    @Size(max = 100)
    @Column(nullable = true)
    private String name;
    
    // Password is now managed by Auth0, keeping field for backwards compatibility
    @Size(max = 255)
    @Column(name = "password_hash")
    private String passwordHash;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    
    @Size(max = 500)
    @Column(name = "imagen_url")
    private String imagenUrl;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    @Size(max = 500)
    @Column(name = "banner_url")
    private String bannerUrl;
    
    public User() {
    }
    
    public User(String email, String username, String name, Role role) {
        this.email = email;
        this.username = username;
        this.name = name;
        this.role = role;
    }
    
    public User(String email, String username, String name, String passwordHash, Role role) {
        this.email = email;
        this.username = username;
        this.name = name;
        this.passwordHash = passwordHash;
        this.role = role;
    }
    
    public User updateInformation(String email, String username, String name) {
        this.email = email;
        this.username = username;
        this.name = name;
        return this;
    }
    
    public User updateInformation(String email, String username, String name, Role role, String imagenUrl, String descripcion, String bannerUrl) {
        this.email = email;
        this.username = username;
        this.name = name;
        this.role = role;
        this.imagenUrl = imagenUrl;
        this.descripcion = descripcion;
        this.bannerUrl = bannerUrl;
        return this;
    }
}
