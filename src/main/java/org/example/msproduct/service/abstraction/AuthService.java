package org.example.msproduct.service.abstraction;

import org.example.msproduct.model.client.dto.UserResponseDto;

public interface AuthService {
    UserResponseDto verifyToken(String token);
    boolean hasAuthority(String authority);
}
