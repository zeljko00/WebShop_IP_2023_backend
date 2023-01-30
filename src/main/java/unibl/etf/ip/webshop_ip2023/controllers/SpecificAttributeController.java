package unibl.etf.ip.webshop_ip2023.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibl.etf.ip.webshop_ip2023.model.dto.SpecificAttributeDTO;
import unibl.etf.ip.webshop_ip2023.services.SpecificAttributeService;

@RestController
@RequestMapping("/specificAttributes")
public class SpecificAttributeController {
    private final SpecificAttributeService specificAttributeService;

    public SpecificAttributeController(SpecificAttributeService specificAttributeService) {
        this.specificAttributeService = specificAttributeService;
    }

    @PostMapping
    public ResponseEntity<SpecificAttributeDTO> add(@RequestBody SpecificAttributeDTO specificAttributeDTO) {
        SpecificAttributeDTO result=specificAttributeService.add(specificAttributeDTO);
        if(result!=null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<SpecificAttributeDTO> update(@RequestBody SpecificAttributeDTO specificAttributeDTO) {
        SpecificAttributeDTO result = specificAttributeService.update(specificAttributeDTO);
        if (result != null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        boolean flag = specificAttributeService.delete(id);
        if (flag)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
