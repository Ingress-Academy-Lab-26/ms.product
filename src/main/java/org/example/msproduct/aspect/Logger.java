package org.example.msproduct.aspect;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class Logger {
    @SneakyThrows
    @Around("@annotation(org.example.msproduct.annotation.Loggable)")
    public Object setLog(ProceedingJoinPoint joinPoint) throws Throwable {
        var methodName = joinPoint.getSignature().getName();
        var className = joinPoint.getTarget().getClass().getName();
        var args = Arrays.asList(joinPoint.getArgs());
        try {
            log.info("ActionLog.{}.{}.start", className, methodName);
            log.info("ActionLog.{}.{}.parameters: {}", className, methodName, args);
            var response = joinPoint.proceed();
            if (response != null) {
                log.info("ActionLog.{}.{}.finish {}", className, methodName, response);
            }
            return response;
        } catch (Throwable throwable) {
            log.error("ActionLog.{}.{}.error ", className, methodName, throwable);
            throw throwable;
        }

    }
}