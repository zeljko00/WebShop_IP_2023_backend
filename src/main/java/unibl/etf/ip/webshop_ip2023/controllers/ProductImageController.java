package unibl.etf.ip.webshop_ip2023.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unibl.etf.ip.webshop_ip2023.services.ProductImageService;

@RestController
@RequestMapping("/images")
public class ProductImageController {
    private final ProductImageService productImageService;

    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @GetMapping(path = "{id}",produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("id") long id){
        byte[] image = productImageService.getImage(id);
        if(image!=null)
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
        else
            return  ResponseEntity.notFound().build();
    }
}
