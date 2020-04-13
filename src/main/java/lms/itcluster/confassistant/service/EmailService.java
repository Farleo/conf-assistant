package lms.itcluster.confassistant.service;

public interface EmailService {

    void sendMessage(String to, String subject, String text);
}
