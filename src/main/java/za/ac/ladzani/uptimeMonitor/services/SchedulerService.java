package za.ac.ladzani.uptimeMonitor.services;

import jakarta.mail.MessagingException;

public interface SchedulerService {

    void fire() throws MessagingException;
}
