package unibl.etf.ip.webshop_ip2023.services;

import unibl.etf.ip.webshop_ip2023.model.dto.AttributeDTO;

public interface AttributeService {
    AttributeDTO add(AttributeDTO attributeDTO);
    void delete(long id);
}
