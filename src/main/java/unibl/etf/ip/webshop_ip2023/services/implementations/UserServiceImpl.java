package unibl.etf.ip.webshop_ip2023.services.implementations;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import unibl.etf.ip.webshop_ip2023.dao.UserDAO;
import unibl.etf.ip.webshop_ip2023.model.User;
import unibl.etf.ip.webshop_ip2023.model.dto.UserDTO;
import unibl.etf.ip.webshop_ip2023.model.enums.AccountStatus;
import unibl.etf.ip.webshop_ip2023.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import unibl.etf.ip.webshop_ip2023.util.LoggerBean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final  Pattern p = Pattern.compile(".*[#,$,%,&,_]+.*");
    private final LoggerBean loggerBean;

    public UserServiceImpl(UserDAO userDAO, ModelMapper modelMapper, LoggerBean loggerBean) {
        this.userDAO = userDAO;
        this.modelMapper = modelMapper;
        this.loggerBean = loggerBean;
    }

    public UserDTO findUserByUsername(String username) {
        User user = userDAO.findByUsername(username);
        if (user != null)
            return modelMapper.map(user, UserDTO.class);
        else return null;
    }

    public UserDTO findUserById(long id) {
        try{
        User user = userDAO.findById(id).get();
        if (user != null)
            return modelMapper.map(user, UserDTO.class);
        else return null;
        }catch(Exception e){
            loggerBean.logError(e);
            return null;
        }
    }

    public UserDTO addUser(UserDTO user) {
        User userEntity = modelMapper.map(user, User.class);
        userEntity.setPassword(encoder.encode(user.getPassword()));
        User temp = userDAO.save(userEntity);
        System.out.println(temp.getId());
        UserDTO userDTO = modelMapper.map(temp, UserDTO.class);
        userDTO.setId(temp.getId());
        System.out.println(userDTO.getId());
        return userDTO;
    }

    public UserDTO updateUser(UserDTO user) {
        User userEntity = userDAO.findById(user.getId()).get();
        if (userEntity == null || !validateCredentials(user.getUsername(),user.getPassword()))
            return null;
        userEntity.setCity(user.getCity());
        userEntity.setEmail(user.getEmail());
        userEntity.setFirstname(user.getFirstname());
        userEntity.setLastname(user.getLastname());
        userEntity.setPassword(encoder.encode(user.getPassword()));
        userEntity.setAvatar(user.getAvatar());

        User temp = userDAO.save(userEntity);
        return modelMapper.map(temp, UserDTO.class);
    }

    public void activate(UserDTO user) {
        User userEntity = userDAO.findById(user.getId()).get();
        if (userEntity != null) {
            userEntity.setStatus(user.getStatus());
            User temp = userDAO.save(userEntity);
        }
    }

    public UserDTO register(UserDTO user) {
        System.out.println("hiting!");
        if (userDAO.findByUsername(user.getUsername()) != null)
            return null;
        else if(!validateCredentials(user.getUsername(),user.getPassword()))
        return null;
        else{
            user.setStatus(AccountStatus.NOT_ACTIVATED);
            return addUser(user);

        }
    }
    public boolean validateCredentials(String username,String password){
        System.out.println(username+" "+password);
        if(username.length()>=5 && password.length()>=8){
            Matcher m = p.matcher(password);
            return m.matches();
        }
        else return false;
    }

    public UserDTO login(String username, String password) {
        UserDTO user = findUserByUsername(username);
        if (user == null)
            return null;
        else if (encoder.matches(password, user.getPassword()))
            return user;
        else return null;
    }

    public List<UserDTO> getAllUsers() {
        List<User> list = userDAO.findAll();
        List<UserDTO> result = new ArrayList<UserDTO>();
        list.stream().forEach((u) -> {
            result.add(modelMapper.map(u, UserDTO.class));
        });
        return result;
    }

    public void deleteUserById(long id) {
        userDAO.deleteById(id);
    }

    public UserDTO checkCredentials(String username, String password) {
        UserDTO user = findUserByUsername(username);
        if (user != null && encoder.matches(password, user.getPassword()))
            return user;
        else
            return null;
    }
}
