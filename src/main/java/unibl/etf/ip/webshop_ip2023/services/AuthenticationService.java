package unibl.etf.ip.webshop_ip2023.services;


import unibl.etf.ip.webshop_ip2023.model.exceptions.BlockedAccountException;
import unibl.etf.ip.webshop_ip2023.model.dto.LoginResponse;

public interface AuthenticationService {
    LoginResponse login(String username, String password,boolean sendEmail) throws BlockedAccountException;
    boolean activateAccount(String username,String password,String code);
}
