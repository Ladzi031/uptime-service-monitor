package za.ac.ladzani.uptimeMonitor.aspects;

import feign.FeignException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Aspect
public class UptimeMonitorProxyAspect {

    private final Logger log = Logger.getLogger(UptimeMonitorProxyAspect.class.getName());

    @Around("execution(* za.ac.ladzani.uptimeMonitor.proxies.UptimeMonitorProxy.pingHealthCheckEndpoint(..))")
    public ResponseEntity<String> catchFeignClientExceptions(ProceedingJoinPoint joinPoint) {
        try {
            return (ResponseEntity<String>) joinPoint.proceed();
        } catch (Throwable e) {
            log.info("Feign Client threw exception, will preserve context if severe");
            if(e instanceof FeignException fex) {
                return ResponseEntity.status(fex.status()).build();
            }
            throw new RuntimeException("unhandled exception", e); // preserve stack-trace context
        }
    }
}
