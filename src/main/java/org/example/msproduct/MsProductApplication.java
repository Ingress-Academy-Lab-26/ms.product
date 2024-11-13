package org.example.msproduct;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.msproduct.model.queue.dto.RatingProductDto;
import org.example.msproduct.queue.QueueSender;
import org.example.msproduct.repository.ProductRepository;
import org.example.msproduct.service.abstraction.ProductService;
import org.example.msproduct.util.CacheUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.Map;

@EnableFeignClients
@EnableAspectJAutoProxy
@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class MsProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsProductApplication.class, args);
    }
}
