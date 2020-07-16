package dev.smjeon.commerce.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restController() {
    }

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controller() {
    }

    @Pointcut("execution(* *.*(..))")
    protected void onMethod() {
    }

    @Around("(restController() || controller()) && onMethod()")
    public Object doLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info("{}, Arguments : {}", proceedingJoinPoint.getSignature(), proceedingJoinPoint.getArgs());
        Object retVal = proceedingJoinPoint.proceed();
        logger.info("{}, Return Value : {}", proceedingJoinPoint.getSignature(), retVal);
        return retVal;
    }
}
