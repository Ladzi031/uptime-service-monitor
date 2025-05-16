package za.ac.ladzani.uptimeMonitor.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.ac.ladzani.uptimeMonitor.domain.entity.SiteDetails;

import java.util.UUID;

@Repository
public interface SiteDetailsRepository extends CrudRepository<SiteDetails, UUID> {

}
