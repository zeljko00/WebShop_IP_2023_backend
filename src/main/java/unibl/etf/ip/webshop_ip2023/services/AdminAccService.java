package unibl.etf.ip.webshop_ip2023.services;

import unibl.etf.ip.webshop_ip2023.model.AdminAcc;

public interface AdminAccService {
    AdminAcc login(String username, String password);
}
