package com.staybits.gigmapapi.authentication.interfaces.rest.transform;

import com.staybits.gigmapapi.authentication.domain.model.commands.UpdateUserCommand;
import com.staybits.gigmapapi.authentication.interfaces.rest.resources.UpdateUserResource;

public class UpdateUserCommandFromResourceAssembler {
    public static UpdateUserCommand toCommandFromResource(UpdateUserResource resource, Long userId) {
        return new UpdateUserCommand(userId, resource.email(), resource.username());
    }
}
