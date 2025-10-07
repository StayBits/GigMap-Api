package com.staybits.gigmapapi.authentication.interfaces.rest.resources;

public record UserDetailsResource(Long id, String email, String username, boolean isArtist, ArtistDetailsResource artistDetails) {
    
    public record ArtistDetailsResource(String artistName, String bio, String[] genres) {
    }
}
