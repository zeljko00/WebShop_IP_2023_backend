package unibl.etf.ip.webshop_ip2023.services;

import unibl.etf.ip.webshop_ip2023.model.Category;
import unibl.etf.ip.webshop_ip2023.model.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO addCategory(CategoryDTO category);
    CategoryDTO updateCategory(CategoryDTO category);
    boolean deleteCategory(long category);
    Category findCategoryEntityById(long id);
}
