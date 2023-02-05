package unibl.etf.ip.webshop_ip2023.services.implementations;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import unibl.etf.ip.webshop_ip2023.dao.MessageDAO;
import unibl.etf.ip.webshop_ip2023.dao.UserDAO;
import unibl.etf.ip.webshop_ip2023.model.Message;
import unibl.etf.ip.webshop_ip2023.model.User;
import unibl.etf.ip.webshop_ip2023.model.dto.MessageDTO;
import unibl.etf.ip.webshop_ip2023.services.MessageService;
import unibl.etf.ip.webshop_ip2023.util.LoggerBean;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
     private final MessageDAO messageDAO;
     private final UserDAO userDAO;
    private final LoggerBean loggerBean;
    public MessageServiceImpl(MessageDAO messageDAO, UserDAO userDAO, LoggerBean loggerBean) {
        this.messageDAO = messageDAO;
        this.userDAO = userDAO;
        this.loggerBean = loggerBean;
    }

    public List<MessageDTO> getAll(){
        return messageDAO.findAllByUnread(true).stream().map((m) -> {
            MessageDTO temp=new MessageDTO();
            temp.setId(m.getId());
            temp.setUnread(m.isUnread());
            temp.setContent(m.getContent());
            temp.setUserId(m.getUser().getId());
            temp.setUser(m.getUser().getFirstname()+" "+m.getUser().getLastname());
            return temp;
        }).collect(Collectors.toList());
    }
    public List<MessageDTO> getAllFiltered(String key){
        return messageDAO.findAllByUnreadAndContentContains(true,key).stream().map((m) -> {
            MessageDTO temp=new MessageDTO();
            temp.setId(m.getId());
            temp.setUnread(m.isUnread());
            temp.setContent(m.getContent());
            temp.setUserId(m.getUser().getId());
            temp.setUser(m.getUser().getFirstname()+" "+m.getUser().getLastname());
            return temp;
        }).collect(Collectors.toList());
    }
    public boolean add(MessageDTO messageDTO){
        try {
            User user = userDAO.findById(messageDTO.getUserId()).get();
            Message message=new Message();
            message.setContent(messageDTO.getContent());
            message.setUnread(true);
            message.setUser(user);
            messageDAO.save(message);
            return true;
        }catch(Exception e){
            loggerBean.logError(e);
            return false;
        }
    }
    public void read(long id){
try {
    Message message = messageDAO.findById(id).get();
    message.setUnread(false);
    messageDAO.save(message);
}catch(Exception e){
e.printStackTrace();}
    }
}
