package org.example.msproduct.service.abstraction;

import org.example.msproduct.model.client.response.UserResponse;

public interface AuthService {
    UserResponse verifyToken(String token);
    boolean hasAuthority(String authority);
}
