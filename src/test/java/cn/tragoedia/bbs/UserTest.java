package cn.tragoedia.bbs;

import cn.tragoedia.bbs.entity.User;
import cn.tragoedia.bbs.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Map;

@SpringBootTest
public class UserTest {
    @Resource
    private UserService userService;

    @Test
    public void register_test() {
        User user = new User();
        user.setUsername("tra");
        user.setPassword("123456");
        user.setEmail("407172162@qq.com");
        Map<String, Object> register = userService.register(user);
        System.out.println(register);
    }


}
