package unibl.etf.ip.webshop_ip2023.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibl.etf.ip.webshop_ip2023.model.dto.PurchaseDTO;
import unibl.etf.ip.webshop_ip2023.services.PurchaseService;

import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/buyer/{id}")
    public ResponseEntity<List<PurchaseDTO>> getByBuyer(@PathVariable("id") long id){
        return new ResponseEntity<>(purchaseService.getByBuyer(id), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<PurchaseDTO> add(@RequestBody PurchaseDTO purchaseDTO){
        PurchaseDTO result=purchaseService.add(purchaseDTO);
        if(result!=null)
            return new ResponseEntity<>(result,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
