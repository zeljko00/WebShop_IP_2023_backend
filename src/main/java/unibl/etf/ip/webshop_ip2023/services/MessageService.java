package unibl.etf.ip.webshop_ip2023.services;

import unibl.etf.ip.webshop_ip2023.model.dto.MessageDTO;

import java.util.List;

public interface MessageService {
    List<MessageDTO> getAll();
    boolean add(MessageDTO messageDTO);
}
