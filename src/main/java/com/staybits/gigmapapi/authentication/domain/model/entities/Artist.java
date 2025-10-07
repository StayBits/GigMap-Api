package com.staybits.gigmapapi.authentication.domain.model.entities;

import com.staybits.gigmapapi.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Artist entity
 * Represents the artistic profile information for users who are artists
 */
@Getter
@Setter
@Entity
@Table(name = "artists")
public class Artist extends AuditableModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(name = "artist_name")
    private String artistName;
    
    @Column(columnDefinition = "TEXT")
    private String bio;
    
    @ElementCollection
    @CollectionTable(name = "artist_genres", joinColumns = @JoinColumn(name = "artist_id"))
    @Column(name = "genre")
    private String[] genres;
    
    @OneToOne(mappedBy = "artist")
    private com.staybits.gigmapapi.authentication.domain.model.aggregates.User user;
    
    public Artist() {
        this.genres = new String[0];
    }
    
    public Artist(String artistName, String bio, String[] genres) {
        this();
        this.artistName = artistName;
        this.bio = bio;
        this.genres = genres != null ? genres : new String[0];
    }
}
