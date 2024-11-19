package org.example.msproduct.client;


import org.example.msproduct.client.decoder.CustomErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import org.springframework.http.HttpHeaders;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@FeignClient(name = "ms-auth", url = "${ms.auth.url}", configuration = CustomErrorDecoder.class)
public interface AuthClient {
    @PostMapping("internal/v1/auth/verify")
    void verifyToken(@RequestHeader(AUTHORIZATION) String token);
}
