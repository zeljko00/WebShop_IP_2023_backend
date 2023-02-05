package unibl.etf.ip.webshop_ip2023.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import unibl.etf.ip.webshop_ip2023.model.SupportAc;
import unibl.etf.ip.webshop_ip2023.services.SupportAcService;

@Controller
@RequestMapping("/support")
public class SupportAcController {
    private final SupportAcService supportAcService;

    public SupportAcController(SupportAcService supportAcService) {
        this.supportAcService = supportAcService;
    }

    @GetMapping
    public ResponseEntity<SupportAc> login(@RequestParam("username") String username,@RequestParam("password") String password){
        SupportAc temp=supportAcService.login(username,password);
        if(temp!=null)
            return new ResponseEntity<SupportAc>(temp, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
