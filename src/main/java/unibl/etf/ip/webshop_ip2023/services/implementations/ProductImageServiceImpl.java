package unibl.etf.ip.webshop_ip2023.services.implementations;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import unibl.etf.ip.webshop_ip2023.dao.ProductImageDAO;
import unibl.etf.ip.webshop_ip2023.model.ProductImage;
import unibl.etf.ip.webshop_ip2023.services.ProductImageService;
import unibl.etf.ip.webshop_ip2023.util.LoggerBean;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProductImageServiceImpl implements ProductImageService {
    @Value("${images.repo}")
    private String imagesRepo;
    private final LoggerBean loggerBean;
    private final ProductImageDAO productImageDAO;

    private Map<String, List<byte[]>> uploadedImages = new HashMap<String, List<byte[]>>();

    public ProductImageServiceImpl(LoggerBean loggerBean, ProductImageDAO productImageDAO) {
        this.loggerBean = loggerBean;
        this.productImageDAO = productImageDAO;
    }

    public ProductImage findById(long id) {
        try {
            return productImageDAO.findById(id).get();
        } catch (Exception e) {
            loggerBean.logError(e);
            return null;
        }
    }

    public void delete(long id) {
        productImageDAO.deleteById(id);
    }

    public byte[] getImage(long id) {
        ProductImage image = findById(id);
        if (image == null)
            return null;
        String path = imagesRepo + File.separator + image.getImg();
        try {
            byte[] result = Files.readAllBytes(Paths.get(path));
            return result;
        } catch (Exception e) {
//            e.printStackTrace();
            loggerBean.logError(e);
            return null;
        }
    }

    public void deleteImage(long id) {
        ProductImage image = findById(id);
        delete(image.getId());
        if (image != null) {
            String path = imagesRepo + File.separator + image.getImg();
            File file = new File(path);
            file.delete();
        }
    }

    public boolean saveImage(byte[] data, String id) {
        try {
            if (!uploadedImages.containsKey(id))
                uploadedImages.put(id, new ArrayList<byte[]>());
            uploadedImages.get(id).add(data);
            System.out.println("daved image for rand" + id);
            return true;
        } catch (Exception e) {
//            e.printStackTrace();
            loggerBean.logError(e);
            return false;
        }
    }

    public List<String> storeImages(String id, long productId) {
        List<String> result = new ArrayList<String>();
        try {
            if (uploadedImages.containsKey(id)) {
                List<byte[]> imgsData = uploadedImages.get(id);
                uploadedImages.remove(id);
                int count = 1;
                for (byte[] data : imgsData) {
                    String name = productId + "-" + count++ + ".jpg";
                    String path = imagesRepo + File.separator + name;
                    result.add(name);
                    File file = new File(path);
                    file.createNewFile();
                    Files.write(Paths.get(path), data);
                    System.out.println("saved");
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
loggerBean.logError(e);
        }
        return result;
    }

    public void add(ProductImage productImage) {
        this.productImageDAO.save(productImage);
    }
//    public boolean saveImage(byte[] data, String id){  //IMPLEMENTACIJA
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
//        return true;
//    }
}
