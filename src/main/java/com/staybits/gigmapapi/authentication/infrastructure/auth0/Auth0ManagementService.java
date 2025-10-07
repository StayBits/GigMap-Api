package com.staybits.gigmapapi.authentication.infrastructure.auth0;

import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.client.mgmt.filter.UserFilter;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.json.mgmt.users.User;
import com.auth0.net.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Auth0 Management Service
 * <p>
 * Service to interact with Auth0 Management API for user CRUD operations.
 * This allows creating, reading, updating and deleting users in Auth0 from the backend.
 * </p>
 */
@Service
public class Auth0ManagementService {

    @Value("${auth0.domain}")
    private String domain;

    @Value("${auth0.management.client-id}")
    private String clientId;

    @Value("${auth0.management.client-secret}")
    private String clientSecret;

    @Value("${auth0.audience}")
    private String audience;

    private ManagementAPI managementAPI;

    /**
     * Get an access token for the Management API
     */
    private String getManagementToken() throws Auth0Exception {
        AuthAPI authAPI = AuthAPI.newBuilder(domain, clientId, clientSecret).build();
        TokenHolder holder = authAPI.requestToken("https://" + domain + "/api/v2/").execute().getBody();
        return holder.getAccessToken();
    }

    /**
     * Initialize Management API with a fresh token
     */
    private ManagementAPI getManagementAPI() throws Auth0Exception {
        if (managementAPI == null) {
            String token = getManagementToken();
            managementAPI = ManagementAPI.newBuilder(domain, token).build();
        }
        return managementAPI;
    }

    /**
     * Create a new user in Auth0
     * @param email User email
     * @param password User password
     * @param username Username
     * @param connection Connection type (default: Username-Password-Authentication)
     * @return Created user
     */
    public User createUser(String email, String password, String username, String connection) throws Auth0Exception {
        ManagementAPI mgmt = getManagementAPI();
        
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password.toCharArray());
        newUser.setUsername(username);
        newUser.setConnection(connection != null ? connection : "Username-Password-Authentication");
        
        // Optional: Add metadata
        Map<String, Object> userMetadata = new HashMap<>();
        userMetadata.put("created_from", "backend");
        newUser.setUserMetadata(userMetadata);

        Response<User> response = mgmt.users().create(newUser).execute();
        return response.getBody();
    }

    /**
     * Create a new user in Auth0 (simplified version)
     */
    public User createUser(String email, String password, String username) throws Auth0Exception {
        return createUser(email, password, username, null);
    }

    /**
     * Get user by ID
     */
    public User getUserById(String userId) throws Auth0Exception {
        ManagementAPI mgmt = getManagementAPI();
        Response<User> response = mgmt.users().get(userId, null).execute();
        return response.getBody();
    }

    /**
     * Get user by email
     */
    public List<User> getUserByEmail(String email) throws Auth0Exception {
        ManagementAPI mgmt = getManagementAPI();
        UserFilter filter = new UserFilter().withQuery("email:\"" + email + "\"");
        var response = mgmt.users().list(filter).execute();
        return response.getBody().getItems();
    }

    /**
     * Update user metadata
     */
    public User updateUserMetadata(String userId, Map<String, Object> metadata) throws Auth0Exception {
        ManagementAPI mgmt = getManagementAPI();
        
        User user = new User();
        user.setUserMetadata(metadata);
        
        Response<User> response = mgmt.users().update(userId, user).execute();
        return response.getBody();
    }

    /**
     * Delete user
     */
    public void deleteUser(String userId) throws Auth0Exception {
        ManagementAPI mgmt = getManagementAPI();
        mgmt.users().delete(userId).execute();
    }

    /**
     * List all users (paginated)
     */
    public List<User> listUsers(int page, int perPage) throws Auth0Exception {
        ManagementAPI mgmt = getManagementAPI();
        UserFilter filter = new UserFilter().withPage(page, perPage);
        var response = mgmt.users().list(filter).execute();
        return response.getBody().getItems();
    }
}
