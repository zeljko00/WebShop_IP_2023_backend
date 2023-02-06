package unibl.etf.ip.webshop_ip2023.services;

import unibl.etf.ip.webshop_ip2023.model.dto.PurchaseDTO;

import java.util.List;

public interface PurchaseService {
    List<PurchaseDTO> getByBuyer(long id);
    PurchaseDTO add(PurchaseDTO purchaseDTO);
    void delete(long id);
}
