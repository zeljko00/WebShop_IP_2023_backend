package unibl.etf.ip.webshop_ip2023.services.implementations;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import unibl.etf.ip.webshop_ip2023.dao.UserDAO;
import unibl.etf.ip.webshop_ip2023.model.User;
import unibl.etf.ip.webshop_ip2023.model.dto.UserDTO;
import unibl.etf.ip.webshop_ip2023.model.enums.AccountStatus;
import unibl.etf.ip.webshop_ip2023.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserDAO userDAO, ModelMapper modelMapper) {
        this.userDAO = userDAO;
        this.modelMapper = modelMapper;
    }

    public UserDTO findUserByUsername(String username){
        User user=userDAO.findByUsername(username);
        if(user!=null)
            return modelMapper.map(user,UserDTO.class);
        else return null;
    }
    public boolean checkCredentials(String username,String password){
        UserDTO userDTO=findUserByUsername(username);
        return userDTO.getPassword().equals(password);
    }
    public UserDTO addUser(UserDTO user){
        User userEntity=modelMapper.map(user,User.class);
        User temp = userDAO.save(userEntity);
        System.out.println(temp.getId());
        UserDTO userDTO=modelMapper.map(temp,UserDTO.class);
        userDTO.setId(temp.getId());
        System.out.println(userDTO.getId());
        return  userDTO;
    }
    public UserDTO updateUser(UserDTO user){
        User userEntity=userDAO.findById(user.getId()).get();
        if(userEntity==null)
            return null;
        userEntity.setCity(user.getCity());
        userEntity.setEmail(user.getEmail());
        userEntity.setFirstname(user.getFirstname());
        userEntity.setLastname(user.getLastname());
        userEntity.setPassword(user.getPassword());

        User temp=userDAO.save(userEntity);
        return modelMapper.map(temp,UserDTO.class);
    }
    public UserDTO register(UserDTO user){
        if(userDAO.findByUsername(user.getUsername())!=null)
            return null;
        else {
            user.setStatus(AccountStatus.NOT_ACTIVATED);
            return  addUser(user);

        }
    }
    public UserDTO login(String username,String password){
        UserDTO user=findUserByUsername(username);
        if(user==null)
            return null;
        else if(user.getPassword().equals(password))
                return user;
        else return  null;
    }
    public List<UserDTO> getAllUsers(){
        List<User> list=userDAO.findAll();
        List<UserDTO> result=new ArrayList<UserDTO>();
        list.stream().forEach((u)->{
            result.add(modelMapper.map(u,UserDTO.class));
        });
        return result;
    }
    public void deleteUserById(long id){
        userDAO.deleteById(id);
    }
}
