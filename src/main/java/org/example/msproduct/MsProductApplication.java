package org.example.msproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsProductApplication.class, args);
    }
}
