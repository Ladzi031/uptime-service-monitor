package za.ac.ladzani.uptimeMonitor.controllers.RESTful;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.ladzani.uptimeMonitor.domain.RegisterServiceRequest;
import za.ac.ladzani.uptimeMonitor.domain.dtos.PingLogDto;
import za.ac.ladzani.uptimeMonitor.domain.dtos.SiteDetailsDto;
import za.ac.ladzani.uptimeMonitor.domain.entity.PingLog;
import za.ac.ladzani.uptimeMonitor.domain.entity.SiteDetails;
import za.ac.ladzani.uptimeMonitor.mappers.PingLogMapper;
import za.ac.ladzani.uptimeMonitor.mappers.SiteDetailsMapper;
import za.ac.ladzani.uptimeMonitor.services.PingLogService;
import za.ac.ladzani.uptimeMonitor.services.SiteDetailsService;
import za.ac.ladzani.uptimeMonitor.utils.Utils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

@RestController
@RequestMapping("api/v1/service-monitoring")
public class ApiSiteController {
    private final SiteDetailsMapper siteDetailsMapper;
    private final PingLogMapper pingLogMapper;
    private final SiteDetailsService siteDetailsService;
    private final PingLogService pingLogService;

    public ApiSiteController(SiteDetailsMapper siteDetailsMapper, PingLogMapper pingLogMapper, SiteDetailsService siteDetailsService, PingLogService pingLogService) {
        this.siteDetailsMapper = siteDetailsMapper;
        this.pingLogMapper = pingLogMapper;
        this.siteDetailsService = siteDetailsService;
        this.pingLogService = pingLogService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerService(@RequestBody @Valid RegisterServiceRequest request) {
        Predicate<SiteDetails> serviceExits = site -> Objects.equals(request.getServiceHealthCheckEndpoint(), site.getServiceHealthCheckEndpoint());
        if(siteDetailsService.getAllRegisteredServices().stream().anyMatch(serviceExits)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("web-service already exists");
        }
        SiteDetails entity = siteDetailsMapper.toEntity(request);
        entity.setSiteId(Utils.getUUID());
        SiteDetails savedEntity = siteDetailsService.registerService(entity);
        return new ResponseEntity<>(siteDetailsMapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllRegisteredServices(@RequestParam(required = false) UUID siteId) {
       if(siteId == null) {
           List<SiteDetailsDto> siteList = siteDetailsService.getAllRegisteredServices()
                   .stream()
                   .map(site -> {
                    var pingLogListDto = pingLogService.findAllPingLogBySiteId(UUID.fromString(site.getSiteId()))
                            .stream()
                            .map(pingLogMapper::toDto)
                            .toList();
                   var siteDetailsDto = siteDetailsMapper.toDto(site);
                   siteDetailsDto.setPingLogs(pingLogListDto);
                   return siteDetailsDto;
                   })
                   .toList();
           return new ResponseEntity<>(siteList, HttpStatus.OK);
       }
       SiteDetails siteDetails = siteDetailsService.getServiceById(siteId);
       List<PingLog> pingLogs = pingLogService.findAllPingLogBySiteId(UUID.fromString(siteDetails.getSiteId()));
       SiteDetailsDto siteDetailsDto = siteDetailsMapper.toDto(siteDetails);
       List<PingLogDto> pingLogDtos = pingLogs.stream().map(pingLogMapper::toDto).toList();
       siteDetailsDto.setPingLogs(pingLogDtos);
       return new ResponseEntity<>(siteDetailsDto, HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<SiteDetailsDto> updateService(@RequestBody SiteDetailsDto newSiteDetail) {
       SiteDetails siteDetails = siteDetailsMapper.toEntity(newSiteDetail);
       SiteDetails updatedSiteDetails = siteDetailsService.updateServiceDetails(siteDetails);
       SiteDetailsDto siteDetailsDto = siteDetailsMapper.toDto(updatedSiteDetails);
        return new ResponseEntity<>(siteDetailsDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{siteId}")
    public ResponseEntity<Void> stopMonitoringService(@PathVariable UUID siteId) {
        siteDetailsService.deleteService(siteId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ping-logs/{siteId}")
    public ResponseEntity<List<PingLogDto>> getAllPingLogsForService(@PathVariable UUID siteId) {
        List<PingLog> pingLogs = pingLogService.findAllPingLogBySiteId(siteId);
        List<PingLogDto> pingLogDto = pingLogs.stream().map(pingLogMapper::toDto).toList();
        return new ResponseEntity<>(pingLogDto, HttpStatus.OK);
    }

    @DeleteMapping("/ping-logs/{siteId}")
    public ResponseEntity<Void> deletePingLogsForService(@PathVariable UUID siteId) {
        pingLogService.deleteAllPingLogsForSite(siteId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/metric/{siteId")
    public ResponseEntity<Double> getSiteMetric(@PathVariable UUID siteId) {
       Double result = pingLogService.calculateUptimePercentage(siteId);
       return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
