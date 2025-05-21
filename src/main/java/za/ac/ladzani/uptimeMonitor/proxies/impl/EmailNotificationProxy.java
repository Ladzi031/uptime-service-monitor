package za.ac.ladzani.uptimeMonitor.proxies.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import za.ac.ladzani.uptimeMonitor.domain.entity.SiteDetails;
import za.ac.ladzani.uptimeMonitor.proxies.NotificationProxy;
import za.ac.ladzani.uptimeMonitor.utils.SpamNotificationHandler;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@Component
public class EmailNotificationProxy implements NotificationProxy {

    private final Logger logger = Logger.getLogger(EmailNotificationProxy.class.getName());

    @Value("${spring.mail.username}")
    private String appEmail;
    @Value("${mail.to}")
    private String personalEmail;

    private final JavaMailSender mailService;
    private final SpamNotificationHandler spamNotificationHandler;

    public EmailNotificationProxy(JavaMailSender mailService, SpamNotificationHandler spamNotificationHandler) {
        this.mailService = mailService;
        this.spamNotificationHandler = spamNotificationHandler;
    }

    @Override
    public void notify(SiteDetails siteDetails,  long httpResponseStatus) throws MessagingException {
        if(spamNotificationHandler.shouldSendAlert(siteDetails.getSiteId())) {
            // you can add some Fancy FreeMarkerTemplates in here and style it to your liking, I just want simple notification.
            SimpleMailMessage message1 = new SimpleMailMessage();
            message1.setFrom(appEmail);
            message1.setTo(personalEmail);
            String SUBJECT = "UPTIME-SERVICE-MONITOR: SERVICE "+ siteDetails.getServiceName() + "IS DOWN";
            message1.setSubject(SUBJECT);
            String body = generateEmailBody(siteDetails, httpResponseStatus);
            message1.setText(body);
            mailService.send(message1);
            spamNotificationHandler.updateSpamPreventionCache(siteDetails.getSiteId());
            logger.info("notification for web service failure: "+ siteDetails.getServiceName() +" has been sent");
        }
    }

    private String generateEmailBody(SiteDetails siteDetails, long httpResponseStatus) {
        return String.format("at %s service: %s was responding with http-status: %d at the %s health-check endpoint.\n\nPlease investigate further.",
                LocalDateTime.now(), siteDetails.getServiceName(), httpResponseStatus, siteDetails.getServiceHealthCheckEndpoint());
    }
}
