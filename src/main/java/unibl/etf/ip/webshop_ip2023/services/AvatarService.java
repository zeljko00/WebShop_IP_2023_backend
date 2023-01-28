package unibl.etf.ip.webshop_ip2023.services;

import java.util.List;

public interface AvatarService {
    List<String> getAllAvatars();
    byte[] getAvatar(String avatar);
}
