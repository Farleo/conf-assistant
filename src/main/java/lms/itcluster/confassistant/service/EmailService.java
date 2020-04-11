package lms.itcluster.confassistant.service;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);
}
