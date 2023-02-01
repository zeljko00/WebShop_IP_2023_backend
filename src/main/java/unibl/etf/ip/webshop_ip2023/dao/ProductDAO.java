package unibl.etf.ip.webshop_ip2023.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import unibl.etf.ip.webshop_ip2023.model.Category;
import unibl.etf.ip.webshop_ip2023.model.Product;
import unibl.etf.ip.webshop_ip2023.model.User;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product,Long> {
    Page<Product> findAll(Pageable pageable);
    Page<Product> findAllBySeller(User seller,Pageable pageable);
    @Query( "SELECT p from Product p WHERE p.price BETWEEN :p1 and :p2 AND p.category=:category AND p.unused=:unused")
    Page<Product> findFiltered1(double p1, double p2, Category category, boolean unused,Pageable pageable);
    @Query( "SELECT p from Product p WHERE p.price BETWEEN :p1 and :p2 AND p.category=:category")
    Page<Product> findFiltered2(double p1, double p2, Category category,Pageable pageable);
    @Query( "SELECT p from Product p WHERE p.price BETWEEN :p1 and :p2 AND p.unused=:unused")
    Page<Product> findFiltered3(double p1, double p2, boolean unused,Pageable pageable);
    @Query( "SELECT p from Product p WHERE p.price BETWEEN :p1 and :p2")
    Page<Product> findFiltered4(double p1, double p2,Pageable pageable);


    @Query( "SELECT p from Product p WHERE p.price BETWEEN :p1 and :p2 AND p.category=:category AND p.unused=:unused  AND p.seller=:user")
    Page<Product> findFiltered5(double p1, double p2, Category category, boolean unused,User user,Pageable pageable);
    @Query( "SELECT p from Product p WHERE p.price BETWEEN :p1 and :p2 AND p.category=:category AND p.seller=:user")
    Page<Product> findFiltered6(double p1, double p2, Category category,User user,Pageable pageable);
    @Query( "SELECT p from Product p WHERE p.price BETWEEN :p1 and :p2 AND p.unused=:unused AND p.seller=:user")
    Page<Product> findFiltered7(double p1, double p2, boolean unused,User user,Pageable pageable);
    @Query( "SELECT p from Product p WHERE p.price BETWEEN :p1 and :p2 AND p.seller=:user")
    Page<Product> findFiltered8(double p1, double p2,User user,Pageable pageable);

    Page<Product> findByPriceIsBetweenAndCategoryAndUnusedAndTitleContains(double p1, double p2, Category category, boolean unused,String title,Pageable pageable);
    Page<Product> findByPriceIsBetweenAndCategoryAndTitleContains(double p1, double p2, Category category,String title,Pageable pageable);
    Page<Product> findByPriceIsBetweenAndUnusedAndTitleContains(double p1, double p2, boolean unused,String title,Pageable pageable);
    Page<Product> findByPriceIsBetweenAndTitleContains(double p1, double p2,String title,Pageable pageable);

    Page<Product> findByPriceIsBetweenAndCategoryAndUnusedAndTitleContainsAndSeller(double p1, double p2, Category category, boolean unused,String title,User user,Pageable pageable);
    Page<Product> findByPriceIsBetweenAndCategoryAndTitleContainsAndSeller(double p1, double p2, Category category,String title,User user,Pageable pageable);
    Page<Product> findByPriceIsBetweenAndUnusedAndTitleContainsAndSeller(double p1, double p2, boolean unused,String title,User user,Pageable pageable);
    Page<Product> findByPriceIsBetweenAndTitleContainsAndSeller(double p1, double p2,String title,User user,Pageable pageable);

//    @Query( "SELECT p from Product p WHERE p.category=:category AND p.unused=:unused")
//    Page<Product> findFiltered5(Category category, boolean unused,Pageable pageable);
//    @Query( "SELECT p from Product p WHERE p.category=:category")
//    Page<Product> findFiltered6(Category category,Pageable pageable);
//    @Query( "SELECT p from Product p WHERE p.unused=:unused")
//    Page<Product> findFiltered7(boolean unused,Pageable pageable);
//    Page<Product> findByCategoryAndTitleContains(Category category,String title,Pageable pageable);
//    Page<Product> findByCategoryAndUnusedAndTitleContains(Category category, boolean unused,String title,Pageable pageable);
//    Page<Product> findByUnusedAndTitleContains(boolean unused,String title,Pageable pageable);
}
