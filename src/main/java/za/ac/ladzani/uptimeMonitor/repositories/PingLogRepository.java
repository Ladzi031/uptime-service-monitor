package za.ac.ladzani.uptimeMonitor.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.ac.ladzani.uptimeMonitor.domain.entity.PingLog;

import java.util.UUID;

@Repository
public interface PingLogRepository extends CrudRepository<PingLog, String> {

    @Query("SELECT * FROM PING_LOG WHERE SITE_ID = :siteId")
    Iterable<PingLog> findAllPingLogById(UUID siteId);
}
