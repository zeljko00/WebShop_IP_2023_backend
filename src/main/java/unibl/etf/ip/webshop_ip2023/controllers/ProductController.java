package unibl.etf.ip.webshop_ip2023.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibl.etf.ip.webshop_ip2023.model.dto.ProductDTO;
import unibl.etf.ip.webshop_ip2023.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/seller/{id}")
    public ResponseEntity<List<ProductDTO>>  getBySeller(@PathVariable("id") long id){
        return new ResponseEntity<List<ProductDTO>>(productService.getProductsBySeller(id), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<ProductDTO>>  getAll(){
        return new ResponseEntity<List<ProductDTO>>(productService.getAllProducts(), HttpStatus.OK);
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
