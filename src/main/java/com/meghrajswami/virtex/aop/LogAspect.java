package com.meghrajswami.virtex.aop;

import com.meghrajswami.virtex.util.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * The aop log aspect for the application.
 */
@Aspect
@Component
public class LogAspect {
    /**
     * The logger pointcut.
     */
    private static final String LOG_POINTCUT = "execution(* com.meghrajswami.virtex.service"
            + ".*.*(..)) || execution(* com.meghrajswami.virtex.controller.rest.*.*(..)) ";
//            + "|| execution(* com.meghrajswami.virtex.security.SimpleUserDetailsService.*(..))";

    /**
     * The logger with package fullName.
     */
    public static final Logger LOGGER = LogManager.getLogger("com.meghrajswami.virtex");

    /**
     * Log method entrance.
     *
     * @param joinPoint the joint point
     */
    @Before(LOG_POINTCUT)
    public void logMethodEntrance(JoinPoint joinPoint) {
        String[] parameterNames = ((MethodSignature) (joinPoint.getSignature())).getParameterNames();
        if (parameterNames == null) {
            parameterNames = new String[joinPoint.getArgs().length];
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                parameterNames[i] = "arg" + i;
            }
        }
        if (joinPoint.getSignature().toString().contains("login")) {
            Helper.logEntrance(LOGGER, joinPoint.getSignature().toString(), new String[]{}, new Object[]{});
        } else {
            Helper.logEntrance(LOGGER, joinPoint.getSignature().toString(), parameterNames, joinPoint.getArgs());
        }
    }

    /**
     * Log method exit.
     *
     * @param joinPoint the join point
     * @param result    the result
     */
    @AfterReturning(pointcut = LOG_POINTCUT, returning = "result")
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        Helper.logExit(LOGGER, joinPoint.getSignature().toString(), result);
    }

    /**
     * Log exception.
     *
     * @param joinPoint the joint point
     * @param ex        the exception
     */
    @AfterThrowing(pointcut = LOG_POINTCUT, throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        Helper.logException(LOGGER, joinPoint.getSignature().toString(), ex);
    }
}
