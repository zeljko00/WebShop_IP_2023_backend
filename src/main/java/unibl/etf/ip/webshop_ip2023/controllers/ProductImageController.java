package unibl.etf.ip.webshop_ip2023.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unibl.etf.ip.webshop_ip2023.services.ProductImageService;

import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ProductImageController {
    private final ProductImageService productImageService;

    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @GetMapping(path = "{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("id") long id){
        byte[] image = productImageService.getImage(id);
        if(image!=null)
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
        else
            return  ResponseEntity.notFound().build();
    }
    @PostMapping
    public void uploadImage(Model model, @RequestParam("image") MultipartFile file, @RequestParam("id") String id) throws IOException {
        System.out.println("Image upload hit! + id="+id);
        productImageService.saveImage(file.getBytes(),id);
    }
}
