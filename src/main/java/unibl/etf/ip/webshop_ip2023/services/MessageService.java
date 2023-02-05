package unibl.etf.ip.webshop_ip2023.services;

import unibl.etf.ip.webshop_ip2023.model.dto.MessageDTO;

import java.util.List;

public interface MessageService {
    List<MessageDTO> getAll();
    List<MessageDTO> getAllFiltered(String key);
    void read(long id);
    boolean add(MessageDTO messageDTO);
}
