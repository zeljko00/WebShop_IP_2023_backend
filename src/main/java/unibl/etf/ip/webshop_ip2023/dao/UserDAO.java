package unibl.etf.ip.webshop_ip2023.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unibl.etf.ip.webshop_ip2023.model.User;
@Repository
public interface UserDAO extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
