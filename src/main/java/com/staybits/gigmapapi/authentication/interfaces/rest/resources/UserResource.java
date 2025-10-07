package com.staybits.gigmapapi.authentication.interfaces.rest.resources;

public record UserResource(Long id, String email, String username, boolean isArtist) {
}
