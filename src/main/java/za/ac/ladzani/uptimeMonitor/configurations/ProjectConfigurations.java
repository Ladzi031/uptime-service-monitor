package za.ac.ladzani.uptimeMonitor.configurations;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableFeignClients(basePackages = "za.ac.ladzani.uptimeMonitor.proxies")
@EnableScheduling
@EnableAspectJAutoProxy
public class ProjectConfigurations {}

