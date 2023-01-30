package unibl.etf.ip.webshop_ip2023.services.implementations;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import unibl.etf.ip.webshop_ip2023.dao.AttributeDAO;
import unibl.etf.ip.webshop_ip2023.dao.ProductDAO;
import unibl.etf.ip.webshop_ip2023.model.Product;
import unibl.etf.ip.webshop_ip2023.model.SpecificAttribute;
import unibl.etf.ip.webshop_ip2023.model.dto.AttributeDTO;
import unibl.etf.ip.webshop_ip2023.model.Attribute;
import unibl.etf.ip.webshop_ip2023.services.AttributeService;

@Service
@Transactional
public class AttributeServiceImpl implements AttributeService {
    private final AttributeDAO attributeDAO;
    private final ProductDAO productDAO;
    private final ModelMapper modelMapper;

    public AttributeServiceImpl(AttributeDAO attributeDAO, ProductDAO productDAO, ModelMapper modelMapper) {
        this.attributeDAO = attributeDAO;
        this.productDAO = productDAO;
        this.modelMapper = modelMapper;
    }

    public AttributeDTO add(AttributeDTO attributeDTO){
        Attribute attribute=modelMapper.map(attributeDTO,Attribute.class);
        try{
            Product product=productDAO.findById(attributeDTO.getProductId()).get();
            boolean flag=true;
            for(SpecificAttribute sa:product.getCategory().getSpecificAttributes())
                if(sa.getName().equals(attributeDTO.getName())){
                    flag=false;
                    break;
                }
            if(flag){
                System.out.println("Forbidden attribute");
                return null;
            }
            attribute.setProduct(product);
            AttributeDTO result=modelMapper.map(attributeDAO.save(attribute),AttributeDTO.class);
            result.setProductId(attribute.getProduct().getId());
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public void delete(long id){
        attributeDAO.deleteById(id);
    }
}
