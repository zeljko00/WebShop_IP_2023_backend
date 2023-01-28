package unibl.etf.ip.webshop_ip2023.services.implementations;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import unibl.etf.ip.webshop_ip2023.dao.AvatarDAO;
import unibl.etf.ip.webshop_ip2023.services.AvatarService;

import java.util.List;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {
    private final AvatarDAO avatarDAO;

    public AvatarServiceImpl(AvatarDAO avatarDAO){
        this.avatarDAO=avatarDAO;
    }
    public List<String> getAllAvatars(){
        return avatarDAO.getAvatars();
    }
    public byte[] getAvatar(String avatar){
        return avatarDAO.getAvatarByName(avatar);
    }
}
