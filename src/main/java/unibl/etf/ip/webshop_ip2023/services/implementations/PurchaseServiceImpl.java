package unibl.etf.ip.webshop_ip2023.services.implementations;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import unibl.etf.ip.webshop_ip2023.dao.ProductDAO;
import unibl.etf.ip.webshop_ip2023.dao.PurchaseDAO;
import unibl.etf.ip.webshop_ip2023.dao.UserDAO;
import unibl.etf.ip.webshop_ip2023.model.Product;
import unibl.etf.ip.webshop_ip2023.model.Purchase;
import unibl.etf.ip.webshop_ip2023.model.User;
import unibl.etf.ip.webshop_ip2023.model.dto.ProductDTO;
import unibl.etf.ip.webshop_ip2023.model.dto.PurchaseDTO;
import unibl.etf.ip.webshop_ip2023.services.ProductService;
import unibl.etf.ip.webshop_ip2023.services.PurchaseService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService {
    private final ProductService productService;
    private final ProductDAO productDAO;
    private final UserDAO userDAO;
    private final PurchaseDAO purchaseDAO;
    private final ModelMapper modelMapper;

    public PurchaseServiceImpl(ProductService productService, ProductDAO productDAO, UserDAO userDAO, PurchaseDAO purchaseDAO, ModelMapper modelMapper) {
        this.productService = productService;
        this.productDAO = productDAO;
        this.userDAO = userDAO;
        this.purchaseDAO = purchaseDAO;
        this.modelMapper = modelMapper;
    }

    public List<PurchaseDTO> getByBuyer(long id){
        try{
            User buyer=userDAO.findById(id).get();
            return purchaseDAO.getPurchasesByUser(buyer).stream().map((Purchase p) -> {
                PurchaseDTO temp=modelMapper.map(p,PurchaseDTO.class);
                ProductDTO productDTO=productService.getById(p.getProduct().getId());
                temp.setProductDTO(productDTO);
                temp.setUserID(id);
                return temp;
            }).collect(Collectors.toList());
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<PurchaseDTO>();
        }
    }
    public PurchaseDTO add(PurchaseDTO purchaseDTO){
        try{
            DateFormat df=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            purchaseDTO.setTime(df.format(new Date()));
            User buyer=userDAO.findById(purchaseDTO.getUserID()).get();
            Product product=productDAO.findById(purchaseDTO.getProductDTO().getId()).get();
            if(buyer.getId()==product.getSeller().getId() || !productService.sellProduct(product.getId()))
                throw  new Exception();
            Purchase purchase=modelMapper.map(purchaseDTO,Purchase.class);
            purchase.setProduct(product);
            purchase.setUser(buyer);
            Purchase result=purchaseDAO.save(purchase);
            //ne vraca sve podatke jer u zahtjevu dodju samo osnovnu podaci
            PurchaseDTO temp=modelMapper.map(result,PurchaseDTO.class);
            ProductDTO productDTO=productService.getById(result.getProduct().getId());
            temp.setProductDTO(productDTO);
            return temp;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
