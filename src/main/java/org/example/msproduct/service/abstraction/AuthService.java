package org.example.msproduct.service.abstraction;

public interface AuthService {
    void verifyToken(String token);
    boolean hasAuthority(String authority);
}
