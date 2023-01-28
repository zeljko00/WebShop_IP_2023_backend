package unibl.etf.ip.webshop_ip2023.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Repository
public class AvatarDAO{
    @Value("${images.repo}")
    private String imagesRepo;
    private final List<String> avatars=Arrays.asList("a1.png","a2.png","a3.png","a4.png","a5.png","a6.png","a7.png","a8.png","a9.png","a10.png","a11.png","a12.png","a13.png","a14.png","a15.png","a16.png");
    public List<String> getAvatars(){
        return avatars;
    }
    public byte[] getAvatarByName(String avatar){
        String path=imagesRepo+ File.separator+avatar;
        try{
            byte[] result= Files.readAllBytes(Paths.get(path));
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
