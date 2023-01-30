package unibl.etf.ip.webshop_ip2023.services.implementations;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import unibl.etf.ip.webshop_ip2023.dao.CommentDAO;
import unibl.etf.ip.webshop_ip2023.dao.ProductDAO;
import unibl.etf.ip.webshop_ip2023.dao.UserDAO;
import unibl.etf.ip.webshop_ip2023.model.Comment;
import unibl.etf.ip.webshop_ip2023.model.Product;
import unibl.etf.ip.webshop_ip2023.model.User;
import unibl.etf.ip.webshop_ip2023.model.dto.CommentDTO;
import unibl.etf.ip.webshop_ip2023.services.CommentService;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentDAO commentDAO;
    private final UserDAO userDAO;
    private final ProductDAO productDAO;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentDAO commentDAO, UserDAO userDAO, ProductDAO productDAO, ModelMapper modelMapper) {
        this.commentDAO = commentDAO;
        this.userDAO = userDAO;
        this.productDAO = productDAO;
        this.modelMapper = modelMapper;
    }

    public CommentDTO add(CommentDTO commentDTO){
        try {
            User user = userDAO.findById(Long.parseLong(commentDTO.getCreatorInfo())).get();
            Product product=productDAO.findById(commentDTO.getProductId()).get();
            Comment comment=modelMapper.map(commentDTO, Comment.class);
            comment.setCreator(user);
            comment.setProduct(product);
            Comment result=commentDAO.save(comment);
            CommentDTO resultDTO=modelMapper.map(result,CommentDTO.class);
            resultDTO.setProductId(product.getId());
            resultDTO.setCreatorInfo(user.getFirstname()+" "+user.getLastname());
            return resultDTO;
        }catch(Exception e){
            return null;
        }
    }
    public void delete(long id){
        commentDAO.deleteById(id);
    }
}
