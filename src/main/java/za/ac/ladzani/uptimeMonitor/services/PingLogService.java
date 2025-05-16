package za.ac.ladzani.uptimeMonitor.services;

import za.ac.ladzani.uptimeMonitor.domain.entity.PingLog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PingLogService {
    PingLog savePingLog(PingLog pingLog);
    List<PingLog> findAllPingLogBySiteId(UUID siteId);
    void deleteAllPingLogsForSite(UUID siteId);
    List<PingLog> viewLogsBeforeDate(UUID siteId, LocalDateTime dateTime);
    List<PingLog> viewLogsBetweenDate(UUID siteId, LocalDateTime start, LocalDateTime end);
    List<PingLog> viewLogsAfterDate(UUID siteId, LocalDateTime dateTime);
    PingLog findLatestPingLog(UUID siteId);
    // A nifty System-Outage Gauge
    Double calculateUptimePercentage(UUID siteId);

    // you can add extra stuff in here to meet your needs...
    // but always remember PingLog stays IMMUTABLE! (which isn't enforced at the entity level for easy of use with (jdbcTemplate and hopefully -JPA- in the future) but it should be)
}
