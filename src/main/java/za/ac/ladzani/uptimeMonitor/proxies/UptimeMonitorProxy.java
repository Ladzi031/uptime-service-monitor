package za.ac.ladzani.uptimeMonitor.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;

@FeignClient(name = "uptimeMonitoringServiceProxy", url = "http://THIS-IS-A-PLACEHOLDER.com")
public interface UptimeMonitorProxy {

    @GetMapping
    ResponseEntity<String> pingHealthCheckEndpoint(URI baseUrl);
}
