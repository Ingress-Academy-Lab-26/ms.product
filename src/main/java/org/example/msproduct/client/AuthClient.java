package org.example.msproduct.client;


import org.example.msproduct.client.decoder.CustomErrorDecoder;
import org.example.msproduct.model.client.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ms-auth", url = "${ms.auth.url}", configuration = CustomErrorDecoder.class)
public interface AuthClient {
    @PostMapping("/v1/internal/auth/verify")
    UserResponse verifyToken(@RequestHeader("Authorization") String token);
}
