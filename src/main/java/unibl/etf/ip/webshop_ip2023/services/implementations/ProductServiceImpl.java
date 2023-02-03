package unibl.etf.ip.webshop_ip2023.services.implementations;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import unibl.etf.ip.webshop_ip2023.dao.*;
import unibl.etf.ip.webshop_ip2023.model.*;
import unibl.etf.ip.webshop_ip2023.model.dto.*;
import unibl.etf.ip.webshop_ip2023.services.AttributeService;
import unibl.etf.ip.webshop_ip2023.services.ProductImageService;
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
    private final AttributeService attributeService;
    private final CommentDAO commentDAO;
    private final AttributeDAO attributeDAO;
    private final ProductImageDAO productImageDAO;
    private final ProductImageService productImageService;

    public ProductServiceImpl(ProductDAO productDAO, UserDAO userDAO, CategoryDAO categoryDAO, ModelMapper modelMapper, AttributeService attributeService, CommentDAO commentDAO, AttributeDAO attributeDAO, ProductImageDAO productImageDAO, ProductImageService productImageService) {
        this.productDAO = productDAO;
        this.userDAO = userDAO;
        this.categoryDAO = categoryDAO;
        this.modelMapper = modelMapper;
        this.attributeService = attributeService;
        this.commentDAO = commentDAO;
        this.attributeDAO = attributeDAO;
        this.productImageDAO = productImageDAO;
        this.productImageService = productImageService;
    }

    private ProductDTO map(Product product) {
        ProductDTO result = modelMapper.map(product, ProductDTO.class);
        if (product.getComments() != null)
            result.setComments(product.getComments().stream().map((Comment c) -> {
                CommentDTO commentDTO = modelMapper.map(c, CommentDTO.class);
                commentDTO.setProductId(c.getProduct().getId());
                commentDTO.setCreatorInfo(c.getCreator().getFirstname() + " " + c.getCreator().getLastname());
                commentDTO.setCreatorAvatar(c.getCreator().getAvatar());
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
        SellerInfo info = new SellerInfo();
        info.setId(product.getSeller().getId());
        info.setInfo(product.getSeller().getFirstname() + " " + product.getSeller().getLastname());
        result.setSeller(info);
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

    //    public List<ProductDTO> getAllProducts() {
//        return productDAO.findAll().stream().map(this::map).collect(Collectors.toList());
//    }
    public ProductDTOPage getAllProducts(Pageable pageable) {
        Page<Product> page = productDAO.findAll(pageable);
        ProductDTOPage result = new ProductDTOPage();
        result.setProducts(page.getContent().stream().map(this::map).collect(Collectors.toList()));
        result.setIndex(page.getNumber());
        result.setTotalPages(page.getTotalPages());
        result.setTotalElements(page.getTotalElements());
        return result;
    }

    public ProductDTOPage getProductsBySeller(long id, Pageable pageable) {
        try {
            User seller = userDAO.findById(id).get();
            Page<Product> page = productDAO.findAllBySeller(seller, pageable);
            ProductDTOPage result = new ProductDTOPage();
            result.setProducts(page.getContent().stream().map(this::map).collect(Collectors.toList()));
            result.setIndex(page.getNumber());
            result.setTotalPages(page.getTotalPages());
            result.setTotalElements(page.getTotalElements());
            return result;
        } catch (Exception e) {
            ProductDTOPage temp = new ProductDTOPage();
            temp.setIndex(0);
            temp.setTotalElements(0);
            temp.setTotalPages(1);
            temp.setProducts(new ArrayList<ProductDTO>());
            return temp;
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

    public ProductDTO add(ProductDTO product,String rand) {
        try {
            User seller = userDAO.findById(product.getSeller().getId()).get();
            Category category = categoryDAO.findByName(product.getCategory());
            Product productEntity = modelMapper.map(product, Product.class);
            productEntity.setCategory(category);
            productEntity.setSeller(seller);

            Product result = productDAO.save(productEntity);
            if(product.getAttributes()!=null){
            product.getAttributes().stream().forEach(a -> {
                a.setProductId(result.getId());
            });
            product.getAttributes().stream().forEach(a ->{ attributeService.add(a);});}
            List<String> images=productImageService.storeImages(rand,result.getId());
            images.stream().forEach((String i) -> {
                ProductImage temp=new ProductImage();
                temp.setProduct(result);
                temp.setImg(i);
                productImageService.add(temp);
            });
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
                List<Comment> comments=product.getComments();
                if(comments!=null)
                    for (Comment c: comments)
                        commentDAO.delete(c);
                List<Attribute> attrs=product.getAttributes();
                if(attrs!=null)
                    for(Attribute a: attrs)
                        attributeDAO.delete(a);
                List<ProductImage> images=product.getImages();
                if(images!=null)
                    for(ProductImage i: images)
                        productImageDAO.delete(i);
                productDAO.deleteById(id);
                return true;
            } else return false;
        } catch (Exception e) {
//            e.printStackTrace();
            return false;
        }
    }

    public ProductDTOPage getFiltered(double p1, double p2, String unused, Category category, String title, Pageable pagable) {
        List<Product> result = new ArrayList<Product>();
        List<ProductDTO> resultDTO = new ArrayList<ProductDTO>();
        Page<Product> temp = null;
        boolean flag = "true".equals(unused);
        if (p1 == -1)
            p1 = 0;
        if (p2 == -1)
            p2 = Double.MAX_VALUE;

        if ("true".equals(unused) == false && "false".equals(unused) == false)
            unused = "-";
        try {
            if ("-".equals(title)) {
                if ("-".equals(unused) == false) {
                    if (category != null) {
                        System.out.println("price, cat, unused");
                        temp = productDAO.findFiltered1(p1, p2, category, flag, pagable);
                        result = temp.getContent();

                    } else {
                        System.out.println("price,unused");
                        temp = productDAO.findFiltered3(p1, p2, flag, pagable);
                        result = temp.getContent();

                    }
                } else {
                    if (category != null) {
                        System.out.println("price, cat");
                        temp = productDAO.findFiltered2(p1, p2, category, pagable);
                        result = temp.getContent();
                    } else {
                        temp = productDAO.findFiltered4(p1, p2, pagable);
                        result = temp.getContent();
                    }
                }
            } else {
                if ("-".equals(unused) == false) {
                    if (category != null) {
                        System.out.println("price, cat, unused,title");
                        temp = productDAO.findByPriceIsBetweenAndCategoryAndUnusedAndTitleContains(p1, p2, category, flag, title, pagable);
                        result = temp.getContent();

                    } else {
                        System.out.println("price,unused,title");
                        temp = productDAO.findByPriceIsBetweenAndUnusedAndTitleContains(p1, p2, flag, title, pagable);
                        result = temp.getContent();

                    }
                } else {
                    if (category != null) {
                        System.out.println("price, cat,title");
                        temp = productDAO.findByPriceIsBetweenAndCategoryAndTitleContains(p1, p2, category, title, pagable);
                        result = temp.getContent();
                    } else {
                        temp = productDAO.findByPriceIsBetweenAndTitleContains(p1, p2, title, pagable);
                        result = temp.getContent();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            temp = Page.empty();
        }
        resultDTO = result.stream().map(prod -> {
            return map(prod);
        }).collect(Collectors.toList());
//        if ("-".equals(title) == false) {
//            System.out.println("title filter");
//            resultDTO = resultDTO.stream().filter(product -> {
//                return product.getTitle().contains(title);
//            }).collect(Collectors.toList());
//        }
        ProductDTOPage resultPage = new ProductDTOPage();
        resultPage.setProducts(resultDTO);
        resultPage.setTotalPages(temp.getTotalPages());
        resultPage.setIndex(temp.getNumber());
        resultPage.setTotalElements(temp.getTotalElements());
        return resultPage;
    }

    public ProductDTOPage getFilteredBySeller(double p1, double p2, String unused, Category category, String title,long user, Pageable pageable) {
        try {
            User seller = userDAO.findById(user).get();
            List<Product> result = new ArrayList<Product>();
            List<ProductDTO> resultDTO = new ArrayList<ProductDTO>();
            Page<Product> temp = null;
            boolean flag = "true".equals(unused);
            if (p1 == -1)
                p1 = 0;
            if (p2 == -1)
                p2 = Double.MAX_VALUE;

            if ("true".equals(unused) == false && "false".equals(unused) == false)
                unused = "-";
            try {
                if ("-".equals(title)) {
                    if ("-".equals(unused) == false) {
                        if (category != null) {
                            System.out.println("price, cat, unused");
                            temp = productDAO.findFiltered5(p1, p2, category, flag,seller, pageable);
                            result = temp.getContent();

                        } else {
                            System.out.println("price,unused");
                            temp = productDAO.findFiltered7(p1, p2, flag,seller, pageable);
                            result = temp.getContent();

                        }
                    } else {
                        if (category != null) {
                            System.out.println("price, cat");
                            temp = productDAO.findFiltered6(p1, p2, category,seller, pageable);
                            result = temp.getContent();
                        } else {
                            temp = productDAO.findFiltered8(p1, p2,seller, pageable);
                            result = temp.getContent();
                        }
                    }
                } else {
                    if ("-".equals(unused) == false) {
                        if (category != null) {
                            System.out.println("price, cat, unused,title");
                            temp = productDAO.findByPriceIsBetweenAndCategoryAndUnusedAndTitleContainsAndSeller(p1, p2, category, flag, title,seller, pageable);
                            result = temp.getContent();

                        } else {
                            System.out.println("price,unused,title");
                            temp = productDAO.findByPriceIsBetweenAndUnusedAndTitleContainsAndSeller(p1, p2, flag, title,seller, pageable);
                            result = temp.getContent();

                        }
                    } else {
                        if (category != null) {
                            System.out.println("price, cat,title");
                            temp = productDAO.findByPriceIsBetweenAndCategoryAndTitleContainsAndSeller(p1, p2, category, title,seller, pageable);
                            result = temp.getContent();
                        } else {
                            temp = productDAO.findByPriceIsBetweenAndTitleContainsAndSeller(p1, p2, title,seller, pageable);
                            result = temp.getContent();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                temp = Page.empty();
            }
            resultDTO = result.stream().map(prod -> {
                return map(prod);
            }).collect(Collectors.toList());
//        if ("-".equals(title) == false) {
//            System.out.println("title filter");
//            resultDTO = resultDTO.stream().filter(product -> {
//                return product.getTitle().contains(title);
//            }).collect(Collectors.toList());
//        }
            ProductDTOPage resultPage = new ProductDTOPage();
            resultPage.setProducts(resultDTO);
            resultPage.setTotalPages(temp.getTotalPages());
            resultPage.setIndex(temp.getNumber());
            resultPage.setTotalElements(temp.getTotalElements());
            return resultPage;
        }catch (Exception e) {
            ProductDTOPage temp = new ProductDTOPage();
            temp.setIndex(0);
            temp.setTotalElements(0);
            temp.setTotalPages(1);
            temp.setProducts(new ArrayList<ProductDTO>());
            return temp;
        }
    }
}
