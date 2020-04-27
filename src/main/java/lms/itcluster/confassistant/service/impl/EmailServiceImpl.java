package lms.itcluster.confassistant.service.impl;

import lms.itcluster.confassistant.service.EmailService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Log4j
@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    @Qualifier("defaultExecutorService")
    private ExecutorService executorService;

    @Autowired
    public JavaMailSender emailSender;

    @Override
    public void sendMessage(String to, String subject, String text) {
        try {
            executorService.submit(new EmailItem(to, subject, text));
        } catch (Exception e) {
            log.error(String.format("Can't send email to %s: ", to), e);
        }
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
                log.info(String.format("Email to %s successful sent", to));
            } catch (Exception e) {
                log.error(String.format("Can't send email to %s: ", to), e);
                tryCount--;
                if (tryCount > 0) {
                    log.debug(String.format("Decrement tryCount and try again to send email: tryCount=%d, destinationEmail=%s", tryCount, to));
                    executorService.submit(this);
                } else {
                    log.error(String.format("Email not sent to %s", to));
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