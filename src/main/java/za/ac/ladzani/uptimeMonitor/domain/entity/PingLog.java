package za.ac.ladzani.uptimeMonitor.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "PING_LOG")
public class PingLog implements Comparable<PingLog>, Persistable<String> {


    @Id
    @Column("PING_ID")
    private String pingId;

    @Column("HTTP_RESPONSE_STATUS")
    private Long httpResponseStatus;

    @Column("RESPONSE_TIME_MS")
    private Long responseTimeMs;

    @Column("IS_SUCCESS")
    private Boolean isSuccess;

    @Column("SITE_ID")
    private String siteId;

    @Column("CREATED_AT")
    private String createdAt;

    // you can add extra stuff in here to meet your needs...

    public PingLog() {
    }

    public PingLog(String pingId, Long httpResponseStatus, Long responseTimeMs, Boolean isSuccess, String siteId, String createdAt) {
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



    @Override
    public int compareTo(PingLog pingLog) {
        return LocalDateTime.parse(this.getCreatedAt()).compareTo(LocalDateTime.parse(pingLog.getCreatedAt()));
    }

    @Override
    public String getId() {
        return this.pingId;
    }

    @Override
    public boolean isNew() {
        return true; // hmmm...?
    }
}
