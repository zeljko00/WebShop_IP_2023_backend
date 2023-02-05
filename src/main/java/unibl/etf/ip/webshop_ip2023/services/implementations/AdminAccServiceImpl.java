package unibl.etf.ip.webshop_ip2023.services.implementations;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import unibl.etf.ip.webshop_ip2023.dao.AdminAccDAO;
import unibl.etf.ip.webshop_ip2023.model.AdminAcc;
import unibl.etf.ip.webshop_ip2023.services.AdminAccService;

@Service
@Transactional
public class AdminAccServiceImpl implements AdminAccService {
    private final AdminAccDAO adminAccDAO;

    public AdminAccServiceImpl(AdminAccDAO adminAccDAO) {
        this.adminAccDAO = adminAccDAO;
    }

    public AdminAcc login(String username, String password){
        return adminAccDAO.findByUsernameAndPassword(username,password);
    }
}
