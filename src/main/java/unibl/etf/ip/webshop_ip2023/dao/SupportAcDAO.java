package unibl.etf.ip.webshop_ip2023.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unibl.etf.ip.webshop_ip2023.model.SupportAc;

import java.util.List;
@Repository
public interface SupportAcDAO extends JpaRepository<SupportAc,String> {
    SupportAc findByUsernameAndPassword(String username, String password);
}
