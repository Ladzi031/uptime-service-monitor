package za.ac.ladzani.uptimeMonitor.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import za.ac.ladzani.uptimeMonitor.services.SiteDetailsService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SpamNotificationHandler {
    private final Map<String, ServiceStatus> spamPreventionCache = new ConcurrentHashMap<>();
    private final SiteDetailsService siteDetailsService;
    private final Duration ALERT_INTERVAL_VALUE = Duration.of(30L, ChronoUnit.MINUTES);

    public SpamNotificationHandler(SiteDetailsService siteDetailsService) {
        this.siteDetailsService = siteDetailsService;
    }

    @PostConstruct
    protected void initCache() {
        siteDetailsService.getAllRegisteredServices()
                .forEach(site -> spamPreventionCache.put(site.getSiteId(), new ServiceStatus(null)));
    }

    public boolean shouldSendAlert(String siteId) {
        LocalDateTime checkLastAlert = spamPreventionCache.get(siteId).lastAlert();
        if(Objects.nonNull(checkLastAlert)) {
            return Duration.between(checkLastAlert, LocalDateTime.now()).toMinutes() > ALERT_INTERVAL_VALUE.toMinutes();
        }
        return true;
    }

    // called when shouldSendAlert() returns true
    public void updateSpamPreventionCache(String siteId) {
        spamPreventionCache.put(siteId, new ServiceStatus(LocalDateTime.now()));
    }

    private record ServiceStatus(LocalDateTime lastAlert) {}
}
