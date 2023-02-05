package unibl.etf.ip.webshop_ip2023.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unibl.etf.ip.webshop_ip2023.model.Message;
import java.util.List;

@Repository
public interface MessageDAO extends JpaRepository<Message,Long> {
    List<Message> findAllByUnread(boolean unread);
    List<Message> findAllByUnreadAndContentContains(boolean unread, String content);

}
