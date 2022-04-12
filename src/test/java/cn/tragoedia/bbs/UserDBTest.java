package cn.tragoedia.bbs;

import cn.tragoedia.bbs.entity.User;
import cn.tragoedia.bbs.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
public class UserDBTest {
    @Resource
    private UserRepository userRepository;

    @Test
    public void test_select_user() {
        User user = userRepository.findUserById(101);
        System.out.println(user);

        user = userRepository.findUserByUsername("liubei");
        System.out.println(user);

        user = userRepository.findUserByEmail("nowcoder101@sina.com");
        System.out.println(user);
    }

    @Test
    public void test_insert_user() {
        final Logger logger = LoggerFactory.getLogger(UserDBTest.class);

        User user = new User();
        user.setUsername("test");
        user.setPassword("123456");
        user.setSalt("abc");
        user.setEmail("test@qq.com");
        user.setHeaderUrl("http://www.newcoder.com/101.png");
        user.setCreateTime(new Date());

        logger.debug("this is a test");

        User save = userRepository.save(user);
        System.out.println(save);
    }

    @Test
    public void test_update_user() {
        int i = userRepository.updateStatusById(150, 1);
        System.out.println(i);

        i = userRepository.updateHeaderById(150, "111");
        System.out.println(i);

        i = userRepository.updatePasswordById(150, "aaaaa");
        System.out.println(i);
    }

}
