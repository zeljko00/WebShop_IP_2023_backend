package unibl.etf.ip.webshop_ip2023.services;

import unibl.etf.ip.webshop_ip2023.model.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO findUserByUsername(String username);
    boolean checkCredentials(String username,String password);
    UserDTO addUser(UserDTO user);
    UserDTO updateUser(UserDTO user);
    UserDTO register(UserDTO user);
    public UserDTO login(String username,String password);
    List<UserDTO> getAllUsers();
    void deleteUserById(long id);
}
