package com.example.oauth2resourceserver.api;

import com.example.oauth2resourceserver.configuration.CustomAccessTokenConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.Map;

@RestController
public class ExampleApi {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "Resource available for everyone";
    }

    @GetMapping("/admin")
    @RolesAllowed({"ROLE_ADMIN"})
    public String adminEndpoint() {
        return "Resource available only for admin";
    }

    @GetMapping("/user")
    @RolesAllowed({"ROLE_USER"})
    public Map<String, Object> userEndpoint() {
        return CustomAccessTokenConverter.userDetails();
    }
}
