package za.ac.ladzani.uptimeMonitor.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.ladzani.uptimeMonitor.domain.entity.SiteDetails;
import za.ac.ladzani.uptimeMonitor.exceptions.EntityNotFoundException;
import za.ac.ladzani.uptimeMonitor.repositories.SiteDetailsRepository;
import za.ac.ladzani.uptimeMonitor.services.SiteDetailsService;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class SiteDetailsServiceImpl implements SiteDetailsService {

    private final SiteDetailsRepository siteDetailsRepository;

    public SiteDetailsServiceImpl(SiteDetailsRepository siteDetailsRepository) {
        this.siteDetailsRepository = siteDetailsRepository;
    }

    @Override
    public SiteDetails registerService(SiteDetails siteDetails) {
        return siteDetailsRepository.save(siteDetails);
    }

    @Override
    public List<SiteDetails> getAllRegisteredServices() {
        return StreamSupport.stream(siteDetailsRepository.findAll().spliterator(), false).toList();
    }

    @Override
    public SiteDetails getServiceById(UUID siteId) {
        return siteDetailsRepository.findById(siteId).orElseThrow(() -> new EntityNotFoundException("entity with id "+ siteId + " does not exist"));
    }

    @Override
    public SiteDetails updateServiceDetails(SiteDetails siteDetails) {
        SiteDetails existingSiteDetails = getServiceById(UUID.fromString(siteDetails.getSiteId()));
        existingSiteDetails.setServiceName(siteDetails.getServiceName());
        existingSiteDetails.setServiceHealthCheckEndpoint(siteDetails.getServiceHealthCheckEndpoint());
        return siteDetailsRepository.save(existingSiteDetails);
    }

    @Override
    public void deleteService(UUID siteId) {
        siteDetailsRepository.deleteById(siteId);
    }
}
