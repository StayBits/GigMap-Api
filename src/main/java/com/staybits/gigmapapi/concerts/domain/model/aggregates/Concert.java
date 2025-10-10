package com.staybits.gigmapapi.concerts.domain.model.aggregates;

import com.staybits.gigmapapi.authentication.domain.model.aggregates.User;
import com.staybits.gigmapapi.authentication.domain.model.valueobjects.Role;
import com.staybits.gigmapapi.concerts.domain.model.valueobjects.ConcertStatus;
import com.staybits.gigmapapi.concerts.domain.model.valueobjects.Genre;
import com.staybits.gigmapapi.concerts.domain.model.valueobjects.Venue;
import com.staybits.gigmapapi.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "concerts")
public class Concert extends AuditableAbstractAggregateRoot<Concert> {

    @NotNull
    @Size(max = 200)
    @Column(nullable = false)
    private String title;

    @NotNull
    @Column(nullable = false)
    private Date datehour;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConcertStatus status;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    public Concert() {
        super();
    }

    public Concert(String title, Date datehour, Venue venue, ConcertStatus status, User user, Genre genre) {
        this.title = title;
        this.datehour = datehour;
        this.venue = venue;
        this.status = status;
        this.user = user;
        this.genre = genre;
    }

    /**
     * Business logic to validate that the user has ARTIST role
     */
    public boolean isValidArtist() {
        return this.user != null && this.user.getRole() == Role.ARTIST;
    }
}
