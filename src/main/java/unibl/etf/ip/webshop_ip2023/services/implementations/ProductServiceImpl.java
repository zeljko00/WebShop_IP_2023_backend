package unibl.etf.ip.webshop_ip2023.services.implementations;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import unibl.etf.ip.webshop_ip2023.dao.CategoryDAO;
import unibl.etf.ip.webshop_ip2023.dao.ProductDAO;
import unibl.etf.ip.webshop_ip2023.dao.UserDAO;
import unibl.etf.ip.webshop_ip2023.model.*;
import unibl.etf.ip.webshop_ip2023.model.dto.AttributeDTO;
import unibl.etf.ip.webshop_ip2023.model.dto.CommentDTO;
import unibl.etf.ip.webshop_ip2023.model.dto.ProductDTO;
import unibl.etf.ip.webshop_ip2023.model.dto.ProductImageDTO;
import unibl.etf.ip.webshop_ip2023.services.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;
    private final UserDAO userDAO;
    private final CategoryDAO categoryDAO;
    private final ModelMapper modelMapper;


    public ProductServiceImpl(ProductDAO productDAO, UserDAO userDAO, CategoryDAO categoryDAO, ModelMapper modelMapper) {
        this.productDAO = productDAO;
        this.userDAO = userDAO;
        this.categoryDAO = categoryDAO;
        this.modelMapper = modelMapper;
    }

    private ProductDTO map(Product product) {
        ProductDTO result = modelMapper.map(product, ProductDTO.class);
        if (product.getComments() != null)
            result.setComments(product.getComments().stream().map((Comment c) -> {
                CommentDTO commentDTO = modelMapper.map(c, CommentDTO.class);
                commentDTO.setProductId(c.getProduct().getId());
                commentDTO.setCreatorInfo(c.getCreator().getFirstname() + " " + c.getCreator().getLastname());
                return commentDTO;
            }).collect(Collectors.toList()));
        if (product.getAttributes() != null)
            result.setAttributes(product.getAttributes().stream().map((Attribute a) -> {
                AttributeDTO attributeDTO = modelMapper.map(a, AttributeDTO.class);
                attributeDTO.setProductId(a.getProduct().getId());
                return attributeDTO;
            }).collect(Collectors.toList()));
        if (product.getImages() != null)
            result.setImages(product.getImages().stream().map((ProductImage pi) -> {
                ProductImageDTO productImageDTO = modelMapper.map(pi, ProductImageDTO.class);
                productImageDTO.setProductId(pi.getProduct().getId());
                return productImageDTO;
            }).collect(Collectors.toList()));
        System.out.println(product.getCategory());
        System.out.println(product.getCategory().getName());
        result.setCategory(product.getCategory().getName());
        result.setSeller(product.getSeller().getFirstname() + " " + product.getSeller().getLastname());
        return result;
    }

    public ProductDTO getById(long id) {
        try {
            Product product = productDAO.findById(id).get();
            return map(product);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductDTO> getAllProducts() {
        return productDAO.findAll().stream().map(this::map).collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsBySeller(long id) {
        try {
            User seller = userDAO.findById(id).get();
            return productDAO.findProductsBySeller(seller).stream().map(this::map).collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<ProductDTO>();
        }
    }

    public boolean sellProduct(long id) {
        try {
            Product product = productDAO.findById(id).get();
            if (product.isSold())
                return false;
            else {
                product.setSold(true);
                productDAO.save(product);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public ProductDTO add(ProductDTO product) {
        try {
            User seller = userDAO.findById(Long.parseLong(product.getSeller())).get();
            Category category = categoryDAO.findByName(product.getCategory());
            Product productEntity = modelMapper.map(product, Product.class);
            productEntity.setCategory(category);
            productEntity.setSeller(seller);
            Product result = productDAO.save(productEntity);
            return map(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean delete(long id, long user) {
        try {
            Product product = productDAO.findById(id).get();
            User seller = userDAO.findById(user).get();
            if (product.getSeller().getId() == user) {
                productDAO.deleteById(id);
                return true;
            } else return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
