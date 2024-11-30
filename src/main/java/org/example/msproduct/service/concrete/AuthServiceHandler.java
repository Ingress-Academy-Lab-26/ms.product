package org.example.msproduct.service.concrete;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.msproduct.annotation.Loggable;
import org.example.msproduct.client.AuthClient;
import org.example.msproduct.service.abstraction.AuthService;
import org.springframework.stereotype.Service;

@Slf4j
@Service(value = "authServiceHandler")
@Loggable
@RequiredArgsConstructor
public class AuthServiceHandler implements AuthService {
    private final AuthClient authClient;

    @Override
    public void verifyToken(String token) {
        authClient.verifyToken(token);
    }

    @Override
    public boolean hasAuthority(String authority) {
        verifyToken(authority);
        return true;
    }
}
