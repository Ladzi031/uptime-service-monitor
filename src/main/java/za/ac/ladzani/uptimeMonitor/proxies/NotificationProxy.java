package za.ac.ladzani.uptimeMonitor.proxies;

import za.ac.ladzani.uptimeMonitor.domain.entity.SiteDetails;

public interface NotificationProxy {
    void notify(SiteDetails siteDetails, long httpResponseStatus);
}
