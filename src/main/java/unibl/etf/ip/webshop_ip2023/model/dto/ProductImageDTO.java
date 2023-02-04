package unibl.etf.ip.webshop_ip2023.model.dto;

public class ProductImageDTO {
    private long id;
    private String img;
    private long productId;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "ProductImageDTO{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", productId=" + productId +
                '}';
    }
}
