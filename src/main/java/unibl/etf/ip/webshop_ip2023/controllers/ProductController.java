package unibl.etf.ip.webshop_ip2023.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibl.etf.ip.webshop_ip2023.model.Category;
import unibl.etf.ip.webshop_ip2023.model.dto.ProductDTO;
import unibl.etf.ip.webshop_ip2023.model.dto.ProductDTOPage;
import unibl.etf.ip.webshop_ip2023.services.CategoryService;
import unibl.etf.ip.webshop_ip2023.services.ProductService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/seller/{id}")
    public ResponseEntity<List<ProductDTO>>  getBySeller(@PathVariable("id") long id){
        return new ResponseEntity<List<ProductDTO>>(productService.getProductsBySeller(id), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<ProductDTOPage>  getAll(@RequestParam(defaultValue = "0") int pageNo,
                                                    @RequestParam(defaultValue = "10") int pageSize){
        return new ResponseEntity<ProductDTOPage>(productService.getAllProducts(PageRequest.of(pageNo,pageSize)), HttpStatus.OK);
    }
    @GetMapping("/{p1}/{p2}/{sold}/{title}/{category}")
    public ResponseEntity<ProductDTOPage>  getAll(@PathVariable("p1") double p1, @PathVariable("p2") double p2, @PathVariable("sold") String sold, @PathVariable("title") String title, @PathVariable("category") String category, @RequestParam(defaultValue = "0") int pageNo,
                                                  @RequestParam(defaultValue = "10") int pageSize){
        Category cat=categoryService.findByName(category);
            return new ResponseEntity<ProductDTOPage>(productService.getFiltered(p1,p2,sold,cat,title, PageRequest.of(pageNo,pageSize)), HttpStatus.OK);
    }
    @DeleteMapping("/{user}/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("user") long user, @PathVariable("id") long id){
        boolean flag=productService.delete(id,user);
        if(flag)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
//    @PutMapping("/{id}")
//    public ResponseEntity<?> sell(@PathVariable("id") long id){
//        boolean flag=productService.sellProduct(id);
//        if(flag)
//            return new ResponseEntity<>(HttpStatus.OK);
//        else
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//    }
    @PostMapping
    public ResponseEntity<ProductDTO> add(@RequestBody ProductDTO productDTO){
        ProductDTO result=productService.add(productDTO);
        if(result!=null)
            return new ResponseEntity<>(result,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
