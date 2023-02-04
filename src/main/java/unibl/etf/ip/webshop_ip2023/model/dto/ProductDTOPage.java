package unibl.etf.ip.webshop_ip2023.model.dto;

import java.util.List;

public class ProductDTOPage {
    List<ProductDTO> products;
    private int index;
    private long totalElements;
    private int totalPages;

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return "ProductDTOPage{" +
                "products=" + products +
                ", index=" + index +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                '}';
    }
}
