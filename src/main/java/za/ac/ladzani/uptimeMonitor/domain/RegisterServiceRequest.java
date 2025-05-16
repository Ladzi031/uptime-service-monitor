package za.ac.ladzani.uptimeMonitor.domain;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class RegisterServiceRequest {

    @NotBlank(message = "Service Name is required")
    @Size(min = 3, max = 25, message = "service name should be a min of {min} and a max of {max}")
    private String serviceName;

    @NotBlank(message = "health check endpoint is required")
    @Pattern(
            regexp = "^(https?:\\/\\/)((localhost)|(([a-zA-Z0-9\\-]+\\.)+[a-zA-Z]{2,}))(:\\d{1,5})?(\\/[^\\s]*)?$",
            message = "URL provided is malformed, full url-name is required"
    )
    private String serviceHealthCheckEndpoint;

    public RegisterServiceRequest() {
    }

    public RegisterServiceRequest(String serviceName, String serviceHealthCheckEndpoint) {
        this.serviceName = serviceName;
        this.serviceHealthCheckEndpoint = serviceHealthCheckEndpoint;
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
}
