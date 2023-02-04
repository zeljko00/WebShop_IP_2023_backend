package unibl.etf.ip.webshop_ip2023.services.implementations;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import unibl.etf.ip.webshop_ip2023.dao.CategoryDAO;
import unibl.etf.ip.webshop_ip2023.dao.SpecificAttributeDAO;
import unibl.etf.ip.webshop_ip2023.model.Category;
import unibl.etf.ip.webshop_ip2023.model.SpecificAttribute;
import unibl.etf.ip.webshop_ip2023.model.dto.SpecificAttributeDTO;
import unibl.etf.ip.webshop_ip2023.services.SpecificAttributeService;
import unibl.etf.ip.webshop_ip2023.util.LoggerBean;

@Service
@Transactional
public class SpecificAttributeServiceImpl implements SpecificAttributeService {
    private final SpecificAttributeDAO specificAttributeDAO;
    private final CategoryDAO categoryDAO;
    private final ModelMapper modelMapper;
    private final LoggerBean loggerBean;

    public SpecificAttributeServiceImpl(SpecificAttributeDAO specificAttributeDAO, CategoryDAO categoryDAO, ModelMapper modelMapper, LoggerBean loggerBean) {
        this.specificAttributeDAO = specificAttributeDAO;
        this.categoryDAO = categoryDAO;
        this.modelMapper = modelMapper;
        this.loggerBean = loggerBean;
    }

    public boolean delete(long id) {
        try {
            SpecificAttribute specificAttribute = specificAttributeDAO.findById(id).get();
            if (specificAttribute != null) {
                specificAttributeDAO.deleteById(id);
                return true;
            } else return false;
        } catch (Exception e) {
            loggerBean.logError(e);
            return false;
        }
    }

    public SpecificAttributeDTO update(SpecificAttributeDTO specificAttributeDTO) {
        try {
            System.out.println(specificAttributeDTO.getId());
            SpecificAttribute temp = specificAttributeDAO.findById(specificAttributeDTO.getId()).get();
            temp.setName(specificAttributeDTO.getName());
            SpecificAttribute result = specificAttributeDAO.save(temp);
            SpecificAttributeDTO resultDTO=new SpecificAttributeDTO();
            resultDTO.setName(result.getName());
            resultDTO.setId(result.getId());
            resultDTO.setCategory(result.getCategory().getId());
            return resultDTO;
        } catch (Exception e) {
//            e.printStackTrace();
            loggerBean.logError(e);
            return null;
        }
    }

    public SpecificAttributeDTO add(SpecificAttributeDTO specificAttributeDTO) {
        try {
            SpecificAttribute temp = modelMapper.map(specificAttributeDTO, SpecificAttribute.class);
            Category category = categoryDAO.findById(specificAttributeDTO.getCategory()).get();
            if (category != null) {
                System.out.println("Found cat");
                temp.setCategory(category);
                SpecificAttribute result = specificAttributeDAO.save(temp);
                SpecificAttributeDTO resultDTO=new SpecificAttributeDTO();
                resultDTO.setName(result.getName());
                resultDTO.setId(result.getId());
                resultDTO.setCategory(result.getCategory().getId());
                return resultDTO;
            }
            return null;
        } catch (Exception e) {
            loggerBean.logError(e);
            return null;
        }
    }
}
