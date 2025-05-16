package za.ac.ladzani.uptimeMonitor.services.impl;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import za.ac.ladzani.uptimeMonitor.domain.entity.PingLog;
import za.ac.ladzani.uptimeMonitor.domain.entity.SiteDetails;
import za.ac.ladzani.uptimeMonitor.proxies.NotificationProxy;
import za.ac.ladzani.uptimeMonitor.proxies.UptimeMonitorProxy;
import za.ac.ladzani.uptimeMonitor.services.PingLogService;
import za.ac.ladzani.uptimeMonitor.services.SchedulerService;
import za.ac.ladzani.uptimeMonitor.services.SiteDetailsService;
import za.ac.ladzani.uptimeMonitor.utils.Utils;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    private final UptimeMonitorProxy uptimeMonitorProxy;
    private final PingLogService pingLogService;
    private final SiteDetailsService siteDetailsService;
    private final NotificationProxy notificationProxy;

    public SchedulerServiceImpl(UptimeMonitorProxy uptimeMonitorProxy, PingLogService pingLogService, SiteDetailsService siteDetailsService, NotificationProxy notificationProxy) {
        this.uptimeMonitorProxy = uptimeMonitorProxy;
        this.pingLogService = pingLogService;
        this.siteDetailsService = siteDetailsService;
        this.notificationProxy = notificationProxy;
    }

    
    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedRate = 6L, initialDelay = 1L)
    @Override
    public void fire() {
        List<SiteDetails> services = siteDetailsService.getAllRegisteredServices();
        for(SiteDetails service: services) {
            PingLog result = runPing(service);
            if(!result.getSuccess()) {
                notificationProxy.notify(service, result.getHttpResponseStatus());
                pingLogService.savePingLog(result);
            }else {
                System.out.println("FROM scheduler after first run: "+ result.getSiteId());
                PingLog latestPingLog = pingLogService.findLatestPingLog(UUID.fromString(result.getSiteId()));
                if(latestPingLog == null) {
                    pingLogService.savePingLog(result);
                }else {

                    final long RESPONSE_TIME_THRESHOLD_MS = 5;

                    // check if the new Ping results are worthy of persisting to storage, to avoid saving nearly similar ping logs
                    boolean persistLog = !Objects.equals(result.getHttpResponseStatus(), latestPingLog.getHttpResponseStatus()) || Math.abs(result.getResponseTimeMs() - latestPingLog.getResponseTimeMs()) > RESPONSE_TIME_THRESHOLD_MS || result.getSuccess() != latestPingLog.getSuccess();
                    if(persistLog) {
                        PingLog newPingLog = new PingLog(Utils.getUUID(), result.getHttpResponseStatus(), result.getResponseTimeMs(), result.getSuccess(), result.getSiteId(), result.getCreatedAt());
                        pingLogService.savePingLog(newPingLog);
                    }
                }

            }
        }
    }

    private PingLog runPing(SiteDetails service) {
        URI baseUrl = URI.create(service.getServiceHealthCheckEndpoint());
        long start = System.currentTimeMillis();
        ResponseEntity<String> serviceResponse = uptimeMonitorProxy.pingHealthCheckEndpoint(baseUrl);
        long responseTimeMs = System.currentTimeMillis() - start;
        HttpStatusCode httpStatusCode = serviceResponse.getStatusCode();
        boolean isSuccess = !httpStatusCode.isError();
        long httpResponseStatus = httpStatusCode.value();
        String siteId = service.getSiteId();
        String createdAt = LocalDateTime.now().toString();
        return new PingLog(Utils.getUUID(), httpResponseStatus, responseTimeMs, isSuccess, siteId, createdAt);
    }


}
