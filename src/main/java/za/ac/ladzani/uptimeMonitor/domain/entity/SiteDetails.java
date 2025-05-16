package za.ac.ladzani.uptimeMonitor.domain.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table(name = "SITE_DETAILS")
public class SiteDetails implements Persistable<String> {

    @Id
    @Column("SITE_ID")
    private String siteId;

    @Column("SERVICE_NAME")
    private String serviceName;

    @Column("SERVICE_HEALTH_CHECK_ENDPOINT")
    private String serviceHealthCheckEndpoint;



    public SiteDetails() {
    }

    public SiteDetails(String siteId, String serviceName, String serviceHealthCheckEndpoint) {
        this.siteId = siteId;
        this.serviceName = serviceName;
        this.serviceHealthCheckEndpoint = serviceHealthCheckEndpoint;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
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


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SiteDetails that = (SiteDetails) o;
        return Objects.equals(siteId, that.siteId) && Objects.equals(serviceName, that.serviceName) && Objects.equals(serviceHealthCheckEndpoint, that.serviceHealthCheckEndpoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(siteId, serviceName, serviceHealthCheckEndpoint);
    }

    @Override
    public String getId() {
        return this.siteId;
    }

    @Override
    public boolean isNew() {
        return true;
    }


}

