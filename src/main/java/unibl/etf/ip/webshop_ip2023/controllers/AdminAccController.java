package unibl.etf.ip.webshop_ip2023.controllers;

import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unibl.etf.ip.webshop_ip2023.model.AdminAcc;
import unibl.etf.ip.webshop_ip2023.services.AdminAccService;

@RestController
@RequestMapping("/admin")
public class AdminAccController {
    private final AdminAccService adminAccService;

    public AdminAccController(AdminAccService adminAccService) {
        this.adminAccService = adminAccService;
    }

    @PostMapping
    public ResponseEntity<AdminAcc> login(@PathParam("username") String username, @PathParam("password") String password){
        AdminAcc temp=adminAccService.login(username,password);
        if(temp!=null)
            return new ResponseEntity<>(temp, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
