package unibl.etf.ip.webshop_ip2023.services.implementations;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import unibl.etf.ip.webshop_ip2023.dao.CategoryDAO;
import unibl.etf.ip.webshop_ip2023.model.Category;
import unibl.etf.ip.webshop_ip2023.model.SpecificAttribute;
import unibl.etf.ip.webshop_ip2023.model.dto.CategoryDTO;
import unibl.etf.ip.webshop_ip2023.model.dto.SpecificAttributeDTO;
import unibl.etf.ip.webshop_ip2023.services.CategoryService;
import unibl.etf.ip.webshop_ip2023.services.SpecificAttributeService;
import unibl.etf.ip.webshop_ip2023.util.LoggerBean;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDAO categoryDAO;
    private final SpecificAttributeService specificAttributeService;
    private final ModelMapper  modelMapper;
    private final LoggerBean loggerBean;

    public CategoryServiceImpl(CategoryDAO categoryDAO, SpecificAttributeService specificAttributeService, ModelMapper modelMapper, LoggerBean loggerBean) {
        this.categoryDAO = categoryDAO;
        this.specificAttributeService = specificAttributeService;
        this.modelMapper = modelMapper;
        this.loggerBean = loggerBean;
    }

    public List<CategoryDTO> getAllCategories() {

            List<Category> categories=categoryDAO.findAll();
            return categories.stream().map(c-> {
                CategoryDTO temp=new CategoryDTO();
                temp.setId(c.getId());
                temp.setName(c.getName());
                temp.setSpecificAttributes(c.getSpecificAttributes().stream().map(a -> {
                    SpecificAttributeDTO spec=new SpecificAttributeDTO();
                    spec.setCategory(a.getCategory().getId());
                    spec.setId(a.getId());
                    spec.setName(a.getName());
                    return spec;
                }).collect(Collectors.toList()));
                return temp;
            }).collect(Collectors.toList());
    }

    public CategoryDTO addCategory(CategoryDTO category) {
        Category entity=modelMapper.map(category,Category.class);
        return modelMapper.map(categoryDAO.save(entity),CategoryDTO.class);
    }
    public CategoryDTO updateCategory(CategoryDTO category){
        try{
        Category temp=categoryDAO.findById(category.getId()).get();
         if(temp!=null){
             temp.setName(category.getName());
             return modelMapper.map(categoryDAO.save(temp),CategoryDTO.class);
         }
         else return null;
        }catch(Exception e){
//            e.printStackTrace();
            loggerBean.logError(e);
            return null;
        }
    }
    public boolean deleteCategory(long category){
        try{
            Category temp=categoryDAO.findById(category).get();
            if(temp!=null){
                for(SpecificAttribute s: temp.getSpecificAttributes())
                    specificAttributeService.delete(s.getId());
                categoryDAO.deleteById(category);
                return true;
            }
            else return false;
        }catch(Exception e){
//            e.printStackTrace();
            loggerBean.logError(e);
            return false;
        }

    }
    public Category findCategoryEntityById(long id){
        try{
            return categoryDAO.findById(id).get();
        }catch(Exception e){
//            e.printStackTrace();
            loggerBean.logError(e);
            return null;
        }
    }
    public Category findByName(String name){
        try{
            System.out.println(name);
            Category result= categoryDAO.findByName(name);
            if(result!=null)
                return result;
            else return null;
        }catch(Exception e){
//            e.printStackTrace();
            loggerBean.logError(e);
            return null;
        }
    }
}
