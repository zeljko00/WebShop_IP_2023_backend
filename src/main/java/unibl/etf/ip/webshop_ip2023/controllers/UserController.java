package unibl.etf.ip.webshop_ip2023.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibl.etf.ip.webshop_ip2023.model.dto.UserDTO;
import unibl.etf.ip.webshop_ip2023.model.request.LoginRequest;
import unibl.etf.ip.webshop_ip2023.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequest request){
        UserDTO userDTO=userService.login(request.getUsername(),request.getPassword());
        if(userDTO==null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        else
            return  new ResponseEntity<UserDTO>(userDTO,HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO user){
        UserDTO userDTO=userService.register(user);
        if(userDTO!=null)
            return new ResponseEntity<UserDTO>(userDTO,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user){

        //autorizacija

        UserDTO userDTO=userService.updateUser(user);
        if(user==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return  new ResponseEntity<UserDTO>(userDTO,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){

        //autorizacija

        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable long id){

        //autorizacija

        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
