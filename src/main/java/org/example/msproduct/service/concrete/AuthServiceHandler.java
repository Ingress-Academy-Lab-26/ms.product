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
    public UserResponse verifyToken(String token) {
        log.info("ActionLog.User.Token: {}", token);
        var response = authClient.verifyToken(token);
        return response;
    }

    @Override
    public boolean hasAuthority(String authority) {
        log.info("ActionLog.User.Authority: {}", authority);
        var userResponse = verifyToken(authority);
        return userResponse != null && userResponse.getUserId() != null;
    }
}
