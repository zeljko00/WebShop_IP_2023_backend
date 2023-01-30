package unibl.etf.ip.webshop_ip2023.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import unibl.etf.ip.webshop_ip2023.model.Product;
import unibl.etf.ip.webshop_ip2023.model.User;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product,Long> {
    List<Product> findProductsBySeller(User user);
}
