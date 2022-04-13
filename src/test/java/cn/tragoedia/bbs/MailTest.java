package cn.tragoedia.bbs;

import cn.tragoedia.bbs.utils.MailClientUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class MailTest {
    @Resource
    private MailClientUtil mailClientUtil;

    @Test
    public void send() {
        mailClientUtil.sendMail("407172162@qq.com", "test3", "<h1>Hello</h1>");
    }
}
