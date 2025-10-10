package com.staybits.gigmapapi.concerts.interfaces.rest.resources;

import java.sql.Date;
import java.util.List;

public record ConcertResource(
    Long id,
    String name,
    Date date,
    String status,
    String description,
    String image,
    String genre,
    PlatformResource platform,
    VenueResource venue,
    List<Long> attendees
) {
}
