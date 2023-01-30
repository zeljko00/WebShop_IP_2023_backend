package unibl.etf.ip.webshop_ip2023.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibl.etf.ip.webshop_ip2023.model.dto.CategoryDTO;
import unibl.etf.ip.webshop_ip2023.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        return new ResponseEntity<List<CategoryDTO>>(categoryService.getAllCategories(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO category){
        CategoryDTO result=categoryService.addCategory(category);
        if(result!=null)
            return new ResponseEntity<CategoryDTO>(result,HttpStatus.OK);
        else return  new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    @PutMapping
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO category){
        CategoryDTO result=categoryService.updateCategory(category);
        if(result!=null)
            return new ResponseEntity<CategoryDTO>(result,HttpStatus.OK);
        else return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") long id){
        boolean flag=categoryService.deleteCategory(id);
        if(flag)
            return new ResponseEntity<>(HttpStatus.OK);
        else return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
