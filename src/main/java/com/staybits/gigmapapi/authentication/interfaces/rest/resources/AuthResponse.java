package com.staybits.gigmapapi.authentication.interfaces.rest.resources;

public record AuthResponse(
    Long id,
    String email,
    String username,
    boolean isArtist,
    String token,
    String message
) {}
