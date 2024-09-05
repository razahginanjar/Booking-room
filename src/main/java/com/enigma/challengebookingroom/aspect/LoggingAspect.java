package com.enigma.challengebookingroom.aspect;

import com.enigma.challengebookingroom.controller.EmployeeController;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Before("execution(* com.enigma.challengebookingroom.controller.EmployeeController.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering method: {} with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    @After("execution(* com.enigma.challengebookingroom.controller.EmployeeController.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Exiting method: {}", joinPoint.getSignature());
    }

    @AfterThrowing(pointcut = "execution(* com.enigma.challengebookingroom.controller.EmployeeController.*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        logger.error("Exception in method: {} with cause: {}", joinPoint.getSignature(), ex.getCause() != null ? ex.getCause() : "NULL");
    }
}
