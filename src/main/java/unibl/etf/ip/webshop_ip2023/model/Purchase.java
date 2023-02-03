package unibl.etf.ip.webshop_ip2023.model;

import jakarta.persistence.*;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String payment;
    private String time;
    private String productCategory;
    private String productTitle;
    private double productPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user", referencedColumnName = "id")
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
