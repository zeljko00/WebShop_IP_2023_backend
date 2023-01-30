package unibl.etf.ip.webshop_ip2023.services;

import unibl.etf.ip.webshop_ip2023.model.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    List<ProductDTO> getProductsBySeller(long id);
    boolean sellProduct(long id);
    ProductDTO add(ProductDTO product);
    boolean delete(long id,long user);
    ProductDTO getById(long id);
}
