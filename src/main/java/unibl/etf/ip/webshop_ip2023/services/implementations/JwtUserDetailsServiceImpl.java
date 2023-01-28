package unibl.etf.ip.webshop_ip2023.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import unibl.etf.ip.webshop_ip2023.dao.UserDAO;
import unibl.etf.ip.webshop_ip2023.model.User;
import unibl.etf.ip.webshop_ip2023.model.dto.JwtUser;
import unibl.etf.ip.webshop_ip2023.model.enums.AccountStatus;
import unibl.etf.ip.webshop_ip2023.services.JwtUserDetailsService;

@Service
public class JwtUserDetailsServiceImpl implements JwtUserDetailsService {

    private final UserDAO userDAO;
    private final ModelMapper modelMapper;

    public JwtUserDetailsServiceImpl(UserDAO userDAO, ModelMapper modelMapper) {
        this.userDAO = userDAO;
        this.modelMapper = modelMapper;
    }

    // retrieve active user info by specified username
    @Override
    public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userDAO.findByUsernameAndStatus(username, AccountStatus.ACTIVATED);
        if(user!=null){
            System.out.println("Found");
            return modelMapper.map(user,JwtUser.class);
        }
        else{
            System.out.println("User not found!");
            throw new UsernameNotFoundException(username);
        }
    }
}
