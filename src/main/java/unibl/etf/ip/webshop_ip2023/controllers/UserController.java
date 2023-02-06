package unibl.etf.ip.webshop_ip2023.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibl.etf.ip.webshop_ip2023.model.dto.LoginResponse;
import unibl.etf.ip.webshop_ip2023.model.dto.UserDTO;
import unibl.etf.ip.webshop_ip2023.model.enums.AccountStatus;
import unibl.etf.ip.webshop_ip2023.model.request.ActivationRequest;
import unibl.etf.ip.webshop_ip2023.model.request.LoginRequest;
import unibl.etf.ip.webshop_ip2023.services.AuthenticationService;
import unibl.etf.ip.webshop_ip2023.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

//    @PostMapping("/login")
//    public ResponseEntity<UserDTO> login(@RequestBody LoginRequest request){
//        UserDTO userDTO=userService.login(request.getUsername(),request.getPassword());
//        if(userDTO==null)
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        else
//            return  new ResponseEntity<UserDTO>(userDTO,HttpStatus.OK);
//    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        try{
            return  new ResponseEntity<LoginResponse>(authenticationService.login(request.getUsername(),request.getPassword(),true),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping("/activate")
    public ResponseEntity<?> activate(@RequestBody ActivationRequest request){
        boolean flag=authenticationService.activateAccount(request.getUsername(),request.getPassword(),request.getCode());
        if(flag)
            return new  ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody UserDTO user){
        System.out.println("hittin controllre");
        UserDTO userDTO=userService.register(user);
        if(userDTO!=null)
            try {
                return new ResponseEntity<LoginResponse>(authenticationService.login(user.getUsername(), user.getPassword(),false), HttpStatus.OK);
            }catch(Exception e){
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user){

        //autorizacija

        UserDTO userDTO=userService.updateUser(user);
        if(userDTO==null)
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
    @GetMapping("/activated/{id}")
    public ResponseEntity<Boolean> checkActivated(@PathVariable("id") long id){
        UserDTO user=userService.findUserById(id);
        if(user!=null){
            System.out.println(user.getStatus());
            System.out.println(AccountStatus.ACTIVATED);
            return  new ResponseEntity<>(user.getStatus().equals(AccountStatus.ACTIVATED),HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/blocked/{id}")
    public ResponseEntity<Boolean> checkBlocked(@PathVariable("id") long id){
        UserDTO user=userService.findUserById(id);
        if(user!=null){
            return  new ResponseEntity<>(user.getStatus().equals(AccountStatus.BLOCKED),HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
