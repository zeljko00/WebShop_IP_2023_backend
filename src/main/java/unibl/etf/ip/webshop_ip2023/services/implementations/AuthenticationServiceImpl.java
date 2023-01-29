package unibl.etf.ip.webshop_ip2023.services.implementations;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import unibl.etf.ip.webshop_ip2023.model.dto.EmailContent;
import unibl.etf.ip.webshop_ip2023.model.exceptions.BlockedAccountException;
import unibl.etf.ip.webshop_ip2023.model.dto.JwtUser;
import unibl.etf.ip.webshop_ip2023.model.dto.LoginResponse;
import unibl.etf.ip.webshop_ip2023.model.dto.UserDTO;
import unibl.etf.ip.webshop_ip2023.model.enums.AccountStatus;
import unibl.etf.ip.webshop_ip2023.security.JwtUtil;
import unibl.etf.ip.webshop_ip2023.services.AuthenticationService;
import unibl.etf.ip.webshop_ip2023.services.EmailService;
import unibl.etf.ip.webshop_ip2023.services.UserService;
import unibl.etf.ip.webshop_ip2023.model.dto.EmailContent;

import java.util.HashMap;
import java.util.Random;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final EmailService emailService;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;
    private final Random generator;
    private final HashMap<String,String> activationCodes;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserService userService, EmailService emailService, ModelMapper modelMapper, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.emailService = emailService;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;
        generator=new Random();
        activationCodes=new HashMap<String,String>();
    }

    @Override
    public LoginResponse login(String username, String password) throws BlockedAccountException {
        System.out.println("Auth service hit! "+username+" "+password);
        LoginResponse loginResponse = null;
        try {
            //authentication manager authenticates user based on received credentials -
            //manager compares user data from jwt and data gathered by jwtUserDetailsService
            //if authentication fails exception is thrown
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    username, password
                            )
                    );
            //authenticated user
            JwtUser user = (JwtUser) authenticate.getPrincipal();
            //based on role response will contain info about Citizen or CityOfficial
            UserDTO userDTO = userService.findUserById(user.getId());
            loginResponse = modelMapper.map(userDTO, LoginResponse.class);
            if (userDTO.getStatus().equals(AccountStatus.ACTIVATED)) {
                // generating jwt
                String token = jwtUtil.generateToken(user);
                System.out.println(token);
                loginResponse.setToken(token);
            }
            else if(userDTO.getStatus().equals(AccountStatus.BLOCKED)){
                throw new BlockedAccountException();
            }
            else if(userDTO.getStatus().equals(AccountStatus.NOT_ACTIVATED)){
                int code=generator.nextInt(9000)+1000;
                System.out.println("put "+userDTO.getUsername()+" "+code);
                activationCodes.put(userDTO.getUsername(),Integer.toString(code));
                EmailContent email=new EmailContent(userDTO.getEmail(),user.getUsername(),code);
                emailService.sendEmail(email);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;               //logovati
        }
        return loginResponse;
    }

    @Override
    public boolean activateAccount(String username, String password, String code) {
        UserDTO user= userService.checkCredentials(username,password);
        if(user!=null && activationCodes.containsKey(username)){
            boolean flag= activationCodes.get(username).equals(code);
            if(flag){
                activationCodes.remove(username);
                user.setStatus(AccountStatus.ACTIVATED);
                userService.activate(user);
            }
            return flag;
        }
        else {
            System.out.println("User not found!!!");
            return false;
        }
    }
}
