package za.ac.ladzani.uptimeMonitor.services;

import za.ac.ladzani.uptimeMonitor.domain.entity.SiteDetails;

import java.util.List;
import java.util.UUID;

public interface SiteDetailsService {
    SiteDetails registerService(SiteDetails siteDetails);
    List<SiteDetails> getAllRegisteredServices();
    SiteDetails getServiceById(UUID siteId);
    SiteDetails updateServiceDetails(SiteDetails siteDetails);
    void deleteService(UUID siteId);
}
