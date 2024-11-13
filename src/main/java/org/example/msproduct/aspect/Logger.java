package org.example.msproduct.aspect;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class Logger {
    @SneakyThrows
    @Around("@annotation(org.example.msproduct.annotation.Loggable)")
    public Object setLog(ProceedingJoinPoint joinPoint) {
        log.info("ActionLog.Started: {}", joinPoint.getSignature().getName());
        var response = joinPoint.proceed();
        log.info("ActionLog.Finished: {}", joinPoint.getSignature().getName());
        return response;
    }

    @SneakyThrows
    @Around("@annotation(org.example.msproduct.annotation.ElapsedTimeLogger)")
    public Object elapsedTime(ProceedingJoinPoint joinPoint) {
        var startTime = System.nanoTime();
        var response = joinPoint.proceed();
        var elapsedTime = System.nanoTime() - startTime;
        log.info("ActionLog.ElapsedTime:{} - {} ns", joinPoint.getSignature().getName(), elapsedTime);
        return response;
    }
}