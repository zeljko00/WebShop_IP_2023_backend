package unibl.etf.ip.webshop_ip2023.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unibl.etf.ip.webshop_ip2023.model.AdminAcc;
@Repository
public interface AdminAccDAO extends JpaRepository<AdminAcc,String> {
    AdminAcc findByUsernameAndPassword(String username, String password);
}
