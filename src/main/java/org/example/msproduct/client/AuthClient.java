package org.example.msproduct.client;


import org.example.msproduct.client.decoder.CustomErrorDecoder;
import org.example.msproduct.model.client.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-auth", url = "${ms.auth.url}", configuration = CustomErrorDecoder.class)
public interface AuthClient {
    @PostMapping("/v1/auth/verify")
    UserResponseDto verifyToken(@RequestHeader("Authorization") String token);
}
