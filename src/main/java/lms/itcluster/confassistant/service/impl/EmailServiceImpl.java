package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Component
public class EmailServiceImpl implements EmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    @Qualifier("defaultExecutorService")
    private ExecutorService executorService;

    @Autowired
    public JavaMailSender emailSender;

    @Override
    public void sendMessage(String to, String subject, String text) {
        executorService.submit(new EmailItem(to, subject, text));
    }

    protected class EmailItem implements Runnable {
        @Value("${mailSender.tryCount}")
        private int tryCount;
        private String to;
        private String subject;
        private String text;

        EmailItem(String to, String subject, String text) {
            this.to = to;
            this.subject = subject;
            this.text = text;
        }

        @Override
        public void run() {
            try {
                SimpleMailMessage msg = buildMessage();
                emailSender.send(msg);
                LOGGER.info("Email to {} successful sent", to);
            } catch (Exception e) {
                LOGGER.error("Can't send email to " + to + ": " + text, e);
                tryCount--;
                if (tryCount > 0) {
                    LOGGER.debug("Decrement tryCount and try again to send email: tryCount={}, destinationEmail={}", tryCount, to);
                    executorService.submit(this);
                } else {
                    LOGGER.error("Email not sent to " + to);
                }
            }
        }

        SimpleMailMessage buildMessage() {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            return message;
        }
    }
}