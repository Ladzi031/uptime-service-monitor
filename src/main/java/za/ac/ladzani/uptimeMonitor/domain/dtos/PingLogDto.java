package za.ac.ladzani.uptimeMonitor.domain.dtos;

public class PingLogDto {
    private String pingId;
    private Long httpResponseStatus;
    private Long responseTimeMs;
    private Boolean isSuccess;
    private String siteId;
    private String createdAt;

    public PingLogDto() {
    }

    public PingLogDto(String pingId, Long httpResponseStatus, Long responseTimeMs, Boolean isSuccess, String siteId, String createdAt) {
        this.pingId = pingId;
        this.httpResponseStatus = httpResponseStatus;
        this.responseTimeMs = responseTimeMs;
        this.isSuccess = isSuccess;
        this.siteId = siteId;
        this.createdAt = createdAt;
    }

    public String getPingId() {
        return pingId;
    }

    public void setPingId(String pingId) {
        this.pingId = pingId;
    }

    public Long getHttpResponseStatus() {
        return httpResponseStatus;
    }

    public void setHttpResponseStatus(Long httpResponseStatus) {
        this.httpResponseStatus = httpResponseStatus;
    }

    public Long getResponseTimeMs() {
        return responseTimeMs;
    }

    public void setResponseTimeMs(Long responseTimeMs) {
        this.responseTimeMs = responseTimeMs;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
