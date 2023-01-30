package unibl.etf.ip.webshop_ip2023.services;

import unibl.etf.ip.webshop_ip2023.model.dto.CommentDTO;

public interface CommentService {
    CommentDTO add(CommentDTO commentDTO);
    void delete(long id);
}
