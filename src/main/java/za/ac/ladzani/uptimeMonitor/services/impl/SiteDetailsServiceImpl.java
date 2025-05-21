package za.ac.ladzani.uptimeMonitor.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.ladzani.uptimeMonitor.domain.entity.SiteDetails;
import za.ac.ladzani.uptimeMonitor.exceptions.EntityNotFoundException;
import za.ac.ladzani.uptimeMonitor.repositories.SiteDetailsRepository;
import za.ac.ladzani.uptimeMonitor.services.PingLogService;
import za.ac.ladzani.uptimeMonitor.services.SiteDetailsService;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class SiteDetailsServiceImpl implements SiteDetailsService {

    private final SiteDetailsRepository siteDetailsRepository;
    private final PingLogService pingLogService;
    private final Logger logger = Logger.getLogger(SiteDetailsServiceImpl.class.getName());

    public SiteDetailsServiceImpl(SiteDetailsRepository siteDetailsRepository, PingLogService pingLogService) {
        this.siteDetailsRepository = siteDetailsRepository;
        this.pingLogService = pingLogService;
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
        return siteDetailsRepository.findById(siteId.toString()).orElseThrow(() -> new EntityNotFoundException("entity with id "+ siteId + " does not exist"));
    }

    @Override
    public SiteDetails updateServiceDetails(SiteDetails siteDetails) {
        if(siteDetailsRepository.existsById(siteDetails.getSiteId())) {
            var intValue = siteDetailsRepository.update(siteDetails.getServiceName(), siteDetails.getServiceHealthCheckEndpoint(), siteDetails.getSiteId());
            logger.info("update call result: "+ intValue);
            return this.getServiceById(UUID.fromString(siteDetails.getSiteId()));
        }
        throw new EntityNotFoundException("entity with id: "+ siteDetails.getSiteId()+ " does not exists");
    }

    @Override
    public void deleteService(UUID siteId) {
        pingLogService.deleteAllPingLogsForSite(siteId);
        siteDetailsRepository.deleteById(siteId.toString());
    }
}
