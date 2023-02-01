package unibl.etf.ip.webshop_ip2023.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import unibl.etf.ip.webshop_ip2023.model.Category;
import unibl.etf.ip.webshop_ip2023.model.dto.ProductDTO;
import unibl.etf.ip.webshop_ip2023.model.dto.ProductDTOPage;

import java.util.List;

public interface ProductService {
    ProductDTOPage getAllProducts(Pageable pageable);
    List<ProductDTO> getProductsBySeller(long id);
    boolean sellProduct(long id);
    ProductDTO add(ProductDTO product);
    boolean delete(long id,long user);
    ProductDTO getById(long id);
    ProductDTOPage getFiltered(double p1, double p2, String sold, Category category, String title, Pageable pageable);
}
