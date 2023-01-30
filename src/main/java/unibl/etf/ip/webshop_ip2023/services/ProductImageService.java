package unibl.etf.ip.webshop_ip2023.services;

import unibl.etf.ip.webshop_ip2023.model.ProductImage;

public interface ProductImageService {
    ProductImage findById(long id);
    void delete(long id);
    byte[] getImage(long id);
}
