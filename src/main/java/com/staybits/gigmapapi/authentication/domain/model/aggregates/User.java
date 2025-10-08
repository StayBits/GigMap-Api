package com.staybits.gigmapapi.authentication.domain.model.aggregates;

import com.staybits.gigmapapi.authentication.domain.model.entities.Artist;
import com.staybits.gigmapapi.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    
    // Password is now managed by Auth0, keeping field for backwards compatibility
    @Size(max = 255)
    @Column(name = "password_hash")
    private String passwordHash;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "artist_id")
    private Artist artist;
    
    public User() {
    }
    
    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }
    
    public User(String email, String username, String passwordHash) {
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
    }
    
    public User(String email, String username, Artist artist) {
        this(email, username);
        this.artist = artist;
    }
    
    public User updateInformation(String email, String username) {
        this.email = email;
        this.username = username;
        return this;
    }
    
    public boolean isArtist() {
        return this.artist != null;
    }
}
