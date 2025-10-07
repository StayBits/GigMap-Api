package com.staybits.gigmapapi.authentication.interfaces.rest.transform;

import com.staybits.gigmapapi.authentication.domain.model.aggregates.User;
import com.staybits.gigmapapi.authentication.interfaces.rest.resources.UserDetailsResource;

public class UserDetailsResourceFromEntityAssembler {
    
    public static UserDetailsResource toResourceFromEntity(User entity) {
        UserDetailsResource.ArtistDetailsResource artistDetails = null;
        
        if (entity.isArtist() && entity.getArtist() != null) {
            var artist = entity.getArtist();
            artistDetails = new UserDetailsResource.ArtistDetailsResource(
                artist.getArtistName(),
                artist.getBio(),
                artist.getGenres()
            );
        }
        
        return new UserDetailsResource(
            entity.getId(),
            entity.getEmail(),
            entity.getUsername(),
            entity.isArtist(),
            artistDetails
        );
    }
}
