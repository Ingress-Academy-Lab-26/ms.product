package org.example.msproduct.service.concrete;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.msproduct.annotation.Loggable;
import org.example.msproduct.client.AuthClient;
import org.example.msproduct.model.client.response.UserResponse;
import org.example.msproduct.service.abstraction.AuthService;
import org.springframework.stereotype.Service;

@Slf4j
@Service(value = "authServiceHandler")
@RequiredArgsConstructor
public class AuthServiceHandler implements AuthService {
    private final AuthClient authClient;

    @Override
    @Loggable
    public void verifyToken(String token) {
        log.info("ActionLog.User.Token: {}", token);
        authClient.verifyToken(token);
    }

    @Override
    public boolean hasAuthority(String authority) {
        log.info("ActionLog.User.Authority: {}", authority);
        verifyToken(authority);
        return true;
    }
}
