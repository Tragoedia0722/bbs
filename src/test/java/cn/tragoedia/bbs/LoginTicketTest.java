package cn.tragoedia.bbs;

import cn.tragoedia.bbs.entity.LoginTicket;
import cn.tragoedia.bbs.repository.LoginTicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
public class LoginTicketTest {
    @Resource
    private LoginTicketRepository loginTicketRepository;

    @Test
    public void login_ticket_insert_test() {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setTicket("123");
        loginTicket.setUserId(111);
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date());
        loginTicketRepository.save(loginTicket);
    }

    @Test
    public void login_ticket_find_test() {
        LoginTicket loginTicketByTicket = loginTicketRepository.findLoginTicketByTicket("123");
        System.out.println(loginTicketByTicket);
    }

    @Test
    public void login_ticket_update_status_test() {
        int i = loginTicketRepository.updateStatusByTicket("123", 0);
        System.out.println(i);
    }
}
