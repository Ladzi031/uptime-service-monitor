package za.ac.ladzani.uptimeMonitor.proxies.impl;

import org.springframework.stereotype.Component;
import za.ac.ladzani.uptimeMonitor.domain.entity.SiteDetails;
import za.ac.ladzani.uptimeMonitor.proxies.NotificationProxy;
import za.ac.ladzani.uptimeMonitor.utils.SpamNotificationHandler;

import java.util.logging.Logger;

@Component
public class EmailNotificationProxy implements NotificationProxy {

    private final Logger logger = Logger.getLogger(EmailNotificationProxy.class.getName());

    private final SpamNotificationHandler spamNotificationHandler;

    public EmailNotificationProxy(SpamNotificationHandler spamNotificationHandler) {
        this.spamNotificationHandler = spamNotificationHandler;
    }

    @Override
    public void notify(SiteDetails siteDetails,  long httpResponseStatus) {
        if(spamNotificationHandler.shouldSendAlert(siteDetails.getSiteId())) {
            // proceed sending the notification...
            spamNotificationHandler.updateSpamPreventionCache(siteDetails.getSiteId());
            logger.info("notification for web service failure: "+ siteDetails.getServiceName() +" has been sent");
        }else {
            logger.info("web service "+ siteDetails.getServiceName()+ " is down but not sending notification to avoid Spamming");
        }
    }
}
