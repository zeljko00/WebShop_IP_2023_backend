package unibl.etf.ip.webshop_ip2023.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unibl.etf.ip.webshop_ip2023.model.Purchase;
import unibl.etf.ip.webshop_ip2023.model.User;

import java.util.List;


@Repository
public interface PurchaseDAO extends JpaRepository<Purchase,Long> {
    List<Purchase> getPurchasesByUser(User user);

}
