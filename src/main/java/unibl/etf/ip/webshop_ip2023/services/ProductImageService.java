package unibl.etf.ip.webshop_ip2023.services;

import unibl.etf.ip.webshop_ip2023.model.ProductImage;

import java.util.List;

public interface ProductImageService {
    ProductImage findById(long id);
    void delete(long id);
    byte[] getImage(long id);
    boolean saveImage(byte[] data, String id);
    List<String> storeImages(String id, long productId);
    void add(ProductImage productImage);
}
