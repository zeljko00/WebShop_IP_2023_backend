package unibl.etf.ip.webshop_ip2023.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> products;
    @OneToMany(mappedBy="category",fetch=FetchType.LAZY)
    private List<SpecificAttribute> specificAttributes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<SpecificAttribute> getSpecificAttributes() {
        return specificAttributes;
    }

    public void setSpecificAttributes(List<SpecificAttribute> specificAttributes) {
        this.specificAttributes = specificAttributes;
    }
}
