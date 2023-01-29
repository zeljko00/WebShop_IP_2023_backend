package unibl.etf.ip.webshop_ip2023.services;

import unibl.etf.ip.webshop_ip2023.model.dto.EmailContent;

public interface EmailService {
    boolean sendEmail(EmailContent emailContent);
}
