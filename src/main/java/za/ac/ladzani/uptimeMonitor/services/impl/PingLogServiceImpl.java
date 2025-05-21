package za.ac.ladzani.uptimeMonitor.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.ladzani.uptimeMonitor.domain.entity.PingLog;
import za.ac.ladzani.uptimeMonitor.repositories.PingLogRepository;
import za.ac.ladzani.uptimeMonitor.services.PingLogService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class PingLogServiceImpl implements PingLogService {
    private final PingLogRepository logRepository;

    public PingLogServiceImpl(PingLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public PingLog savePingLog(PingLog pingLog) {
        return logRepository.save(pingLog);
    }

    @Override
    public List<PingLog> findAllPingLogBySiteId(UUID siteId) {
        List<PingLog> logList = new ArrayList<>();
        StreamSupport.stream(logRepository.findAllPingLogById(siteId).spliterator(), false).forEach(logList::add);
        return logList;
    }

    @Override
    public void deleteAllPingLogsForSite(UUID siteId) {
        logRepository.deleteAll(logRepository.findAllPingLogById(siteId));
    }

    @Override
    public List<PingLog> viewLogsBeforeDate(UUID siteId, LocalDateTime dateTime) {
        return findAllPingLogBySiteId(siteId).stream().filter(log -> LocalDateTime.parse(log.getCreatedAt()).isBefore(dateTime)).toList();
    }

    @Override
    public List<PingLog> viewLogsBetweenDate(UUID siteId, LocalDateTime start, LocalDateTime end) {
        Predicate<PingLog> isAfterDate = l -> LocalDateTime.parse(l.getCreatedAt()).isAfter(start);
        Predicate<PingLog> isBeforeDate = l -> LocalDateTime.parse(l.getCreatedAt()).isBefore(end);
        return findAllPingLogBySiteId(siteId).stream().filter(isAfterDate.and(isBeforeDate)).toList();
    }

    @Override
    public List<PingLog> viewLogsAfterDate(UUID siteId, LocalDateTime dateTime) {
        return findAllPingLogBySiteId(siteId).stream().filter(log -> LocalDateTime.parse(log.getCreatedAt()).isBefore(dateTime)).toList();
    }

    @Override
    public PingLog findLatestPingLog(UUID siteId) {
        List<PingLog> pingLogs = findAllPingLogBySiteId(siteId);
        if(!pingLogs.isEmpty()) {
            Collections.sort(pingLogs);
            return pingLogs.getLast();
        }
        return null;
    }

    @Override
    public Double calculateUptimePercentage(UUID siteId) {
        long successfulPings = findAllPingLogBySiteId(siteId).stream().filter(PingLog::getSuccess).count();
        long totalPings = (long) findAllPingLogBySiteId(siteId).size();
        if(totalPings > 0) {
            return (successfulPings / totalPings ) * 100.0;
        }
        return -1.0;
    }
}
