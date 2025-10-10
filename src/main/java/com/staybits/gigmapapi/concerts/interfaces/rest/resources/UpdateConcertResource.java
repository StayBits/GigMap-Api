package com.staybits.gigmapapi.concerts.interfaces.rest.resources;

import java.sql.Date;

public record UpdateConcertResource(
    Long id,
    String title,
    String description,
    String imageUrl,
    Date date,
    VenueResource venue,
    String genre,
    String status,
    PlatformResource platform
) {
    public UpdateConcertResource {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id must be greater than 0");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title cannot be null or blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("description cannot be null or blank");
        }
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new IllegalArgumentException("imageUrl cannot be null or blank");
        }
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        if (venue == null || venue.name() == null || venue.name().isBlank() || venue.address() == null || venue.address().isBlank()) {
            throw new IllegalArgumentException("venue cannot be null and must have name and address");
        }
        if (genre == null || genre.isBlank()) {
            throw new IllegalArgumentException("genre cannot be null or blank");
        }
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("status cannot be null or blank");
        }
    }
}
