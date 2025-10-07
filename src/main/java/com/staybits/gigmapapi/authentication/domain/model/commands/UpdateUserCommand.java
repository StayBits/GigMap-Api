package com.staybits.gigmapapi.authentication.domain.model.commands;

public record UpdateUserCommand(Long userId, String email, String username) {
}
