package cn.tragoedia.bbs;

import cn.tragoedia.bbs.utils.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class MailTest {
    @Resource
    private MailClient mailClient;

    @Test
    public void send() {
        mailClient.sendMail("407172162@qq.com", "test3", "<h1>Hello</h1>");
    }
}
