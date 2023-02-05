package unibl.etf.ip.webshop_ip2023.dao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
@Repository
public class StatisticsDAO {
    @Value("${log.file}")
    private String statistics;
    public List<String> readStatistics(){

        List<String> result=new ArrayList<>();
        try{
            BufferedReader br=new BufferedReader(new FileReader((statistics)));
            String line="";
            while((line=br.readLine())!=null)
                result.add(line);
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
