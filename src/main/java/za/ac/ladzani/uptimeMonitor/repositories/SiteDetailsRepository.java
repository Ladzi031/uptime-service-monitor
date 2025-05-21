package za.ac.ladzani.uptimeMonitor.repositories;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.ac.ladzani.uptimeMonitor.domain.entity.SiteDetails;


@Repository
public interface SiteDetailsRepository extends CrudRepository<SiteDetails, String> {

    @Modifying
    @Query("UPDATE site_details SET service_name=:serviceName, service_health_check_endpoint=:serviceHealthCheckEndpoint WHERE site_id=:siteId")
    Integer update(String serviceName, String serviceHealthCheckEndpoint, String siteId);
}
