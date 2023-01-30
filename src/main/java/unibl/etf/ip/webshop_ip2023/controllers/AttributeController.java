package unibl.etf.ip.webshop_ip2023.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibl.etf.ip.webshop_ip2023.model.dto.AttributeDTO;
import unibl.etf.ip.webshop_ip2023.services.AttributeService;

@RestController
@RequestMapping("/attributes")
public class AttributeController {
    private final AttributeService attributeService;

    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @PostMapping
    public ResponseEntity<AttributeDTO> add(@RequestBody AttributeDTO attributeDTO){
        AttributeDTO result=attributeService.add(attributeDTO);
        if(result!=null)
            return new ResponseEntity<AttributeDTO>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        attributeService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
