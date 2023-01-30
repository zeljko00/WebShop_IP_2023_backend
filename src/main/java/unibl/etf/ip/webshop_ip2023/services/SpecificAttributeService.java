package unibl.etf.ip.webshop_ip2023.services;

import unibl.etf.ip.webshop_ip2023.model.dto.SpecificAttributeDTO;

public interface SpecificAttributeService {
    boolean delete(long id);
    SpecificAttributeDTO update(SpecificAttributeDTO specificAttributeDTO);
    SpecificAttributeDTO add(SpecificAttributeDTO specificAttributeDTO);
}
