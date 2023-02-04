package unibl.etf.ip.webshop_ip2023.services.implementations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import unibl.etf.ip.webshop_ip2023.model.dto.EmailContent;
import unibl.etf.ip.webshop_ip2023.services.EmailService;
import unibl.etf.ip.webshop_ip2023.util.LoggerBean;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final LoggerBean loggerBean;
    @Value("${spring.mail.username}") private String sender;

    public EmailServiceImpl(JavaMailSender javaMailSender, LoggerBean loggerBean) {
        this.javaMailSender = javaMailSender;
        this.loggerBean = loggerBean;
    }

    public boolean sendEmail(EmailContent emailContent){
        try {
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(emailContent.getRecipient());
            mailMessage.setText(emailContent.getContent());
            mailMessage.setSubject(emailContent.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            loggerBean.logError(e);
            return false;
        }
    }
}
