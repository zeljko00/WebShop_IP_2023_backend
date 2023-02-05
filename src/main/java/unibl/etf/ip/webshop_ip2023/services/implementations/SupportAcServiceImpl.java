package unibl.etf.ip.webshop_ip2023.services.implementations;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import unibl.etf.ip.webshop_ip2023.dao.SupportAcDAO;
import unibl.etf.ip.webshop_ip2023.model.SupportAc;
import unibl.etf.ip.webshop_ip2023.services.SupportAcService;
@Service
@Transactional
public class SupportAcServiceImpl implements SupportAcService {
    private final SupportAcDAO supportAcDAO;

    public SupportAcServiceImpl(SupportAcDAO supportAcDAO) {
        this.supportAcDAO = supportAcDAO;
    }

    public SupportAc login(String username, String password){
        SupportAc result=supportAcDAO.findByUsernameAndPassword(username,password);
        return result;
    }
}
