package cn.tragoedia.bbs.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class ServiceLogAspect {
    @Pointcut("execution(* cn.tragoedia.bbs.service.*.*(..))")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        String host = request.getRemoteHost();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        long start = System.currentTimeMillis();
        log.info(String.format("--> 用户[%s]，开始执行方法[%s]，参数%s", host, methodName, Arrays.toString(args)));
        Object proceed = joinPoint.proceed();
        long time = System.currentTimeMillis() - start;
        log.info(String.format("<-- 用户[%s]，执行方法[%s]结束，共计用时[%s]ms", host, methodName, time));
        return proceed;
    }
}
