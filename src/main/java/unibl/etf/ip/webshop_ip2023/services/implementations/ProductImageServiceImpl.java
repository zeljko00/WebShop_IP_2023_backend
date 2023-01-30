package unibl.etf.ip.webshop_ip2023.services.implementations;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import unibl.etf.ip.webshop_ip2023.dao.ProductImageDAO;
import unibl.etf.ip.webshop_ip2023.model.ProductImage;
import unibl.etf.ip.webshop_ip2023.services.ProductImageService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@Service
@Transactional
public class ProductImageServiceImpl implements ProductImageService {
    @Value("${images.repo}")
    private String imagesRepo;
    private final ProductImageDAO productImageDAO;

    public ProductImageServiceImpl(ProductImageDAO productImageDAO) {
        this.productImageDAO = productImageDAO;
    }

    public ProductImage findById(long id){
        try{
            return productImageDAO.findById(id).get();
        }catch(Exception e){
            return null;
        }
    }
    public void delete(long id){
        productImageDAO.deleteById(id);
    }
    public boolean add(){return true;}         //implementaCIJA

    public byte[] getImage(long id){
        ProductImage image=findById(id);
        if(image==null)
            return null;
        String path=imagesRepo+ File.separator+image.getImg();
        try{
            byte[] result= Files.readAllBytes(Paths.get(path));
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public void deleteImage(long id){
        ProductImage image=findById(id);
        delete(image.getId());
        if(image!=null) {
            String path=imagesRepo+File.separator+image.getImg();
            File file=new File(path);
            file.delete();
        }
    }
    public boolean saveImage(byte[] data, String id){  //IMPLEMENTACIJA
//        String[] tokens=id.split("--");
//        try{
//            int ident=Integer.parseInt(tokens[0]);
//            if(!uploadedImages.containsKey(Integer.toString(ident)))
//                uploadedImages.put(Integer.toString(ident),new ArrayList<String>());
//            String imageName=id;
//            uploadedImages.get(Integer.toString(ident)).add(imageName);
//
//            String path=imagesRepo+File.separator+imageName;
//
//            File file=new File(path);
//            file.createNewFile();
//            Files.write(Paths.get(path), data);
//            System.out.println("added: "+ident);
//            return true;
//        }catch(Exception e){
//            e.printStackTrace();
//            return false;
//        }
        return true;
    }
}
