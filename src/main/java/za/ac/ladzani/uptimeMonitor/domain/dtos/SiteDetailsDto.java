package za.ac.ladzani.uptimeMonitor.domain.dtos;


import java.util.List;
import java.util.UUID;

public class SiteDetailsDto {

    private UUID siteId;

    private String serviceName;

    private String serviceHealthCheckEndpoint;

    private List<PingLogDto> pingLogs;

    public SiteDetailsDto() {
    }

    public SiteDetailsDto(UUID siteId, String serviceName, String serviceHealthCheckEndpoint) {
        this.siteId = siteId;
        this.serviceName = serviceName;
        this.serviceHealthCheckEndpoint = serviceHealthCheckEndpoint;

    }

    public UUID getSiteId() {
        return siteId;
    }

    public void setSiteId(UUID siteId) {
        this.siteId = siteId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceHealthCheckEndpoint() {
        return serviceHealthCheckEndpoint;
    }

    public void setServiceHealthCheckEndpoint(String serviceHealthCheckEndpoint) {
        this.serviceHealthCheckEndpoint = serviceHealthCheckEndpoint;
    }

    public List<PingLogDto> getPingLogs() {
        return pingLogs;
    }

    public void setPingLogs(List<PingLogDto> pingLogs) {
        this.pingLogs = pingLogs;
    }
}
