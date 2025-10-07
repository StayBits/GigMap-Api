package com.staybits.gigmapapi.authentication.interfaces.rest;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.mgmt.users.User;
import com.staybits.gigmapapi.authentication.infrastructure.auth0.Auth0ManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Auth0 Users Controller (OPCIONAL)
 * <p>
 * Este controller permite gestionar usuarios en Auth0 desde el backend.
 * NOTA: Normalmente los usuarios se registran desde el frontend usando Auth0 SDK.
 * Este controller es útil para:
 * - Crear usuarios de prueba
 * - Administración programática de usuarios
 * - Sincronización de usuarios
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/auth0/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Auth0 User Management", description = "Backend user management with Auth0 Management API")
public class Auth0UsersController {

    private final Auth0ManagementService auth0Service;

    public Auth0UsersController(Auth0ManagementService auth0Service) {
        this.auth0Service = auth0Service;
    }

    /**
     * Create a new user in Auth0
     * NOTA: En producción, los usuarios normalmente se registran desde el frontend
     */
    @PostMapping
    @Operation(summary = "Create user in Auth0", 
               description = "Creates a new user in Auth0. Normally users register from the frontend.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully."),
        @ApiResponse(responseCode = "400", description = "Bad request."),
        @ApiResponse(responseCode = "401", description = "Unauthorized.")
    })
    public ResponseEntity<?> createUser(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String username) {
        try {
            User user = auth0Service.createUser(email, password, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Auth0Exception e) {
            return ResponseEntity.badRequest().body("Error creating user: " + e.getMessage());
        }
    }

    /**
     * Get user by email
     */
    @GetMapping("/search")
    @Operation(summary = "Search user by email", description = "Find Auth0 user by email address")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found."),
        @ApiResponse(responseCode = "404", description = "User not found."),
        @ApiResponse(responseCode = "401", description = "Unauthorized.")
    })
    public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
        try {
            List<User> users = auth0Service.getUserByEmail(email);
            if (users.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(users.get(0));
        } catch (Auth0Exception e) {
            return ResponseEntity.badRequest().body("Error searching user: " + e.getMessage());
        }
    }

    /**
     * Get user by ID
     */
    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID", description = "Get Auth0 user by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found."),
        @ApiResponse(responseCode = "404", description = "User not found."),
        @ApiResponse(responseCode = "401", description = "Unauthorized.")
    })
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        try {
            User user = auth0Service.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (Auth0Exception e) {
            return ResponseEntity.badRequest().body("Error fetching user: " + e.getMessage());
        }
    }

    /**
     * List users (paginated)
     */
    @GetMapping
    @Operation(summary = "List users", description = "List all Auth0 users (paginated)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully."),
        @ApiResponse(responseCode = "401", description = "Unauthorized.")
    })
    public ResponseEntity<?> listUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int perPage) {
        try {
            List<User> users = auth0Service.listUsers(page, perPage);
            return ResponseEntity.ok(users);
        } catch (Auth0Exception e) {
            return ResponseEntity.badRequest().body("Error listing users: " + e.getMessage());
        }
    }

    /**
     * Delete user
     */
    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user", description = "Delete user from Auth0")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully."),
        @ApiResponse(responseCode = "404", description = "User not found."),
        @ApiResponse(responseCode = "401", description = "Unauthorized.")
    })
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        try {
            auth0Service.deleteUser(userId);
            return ResponseEntity.noContent().build();
        } catch (Auth0Exception e) {
            return ResponseEntity.badRequest().body("Error deleting user: " + e.getMessage());
        }
    }
}
