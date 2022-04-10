package cn.tragoedia.bbs;

import cn.tragoedia.bbs.entity.User;
import cn.tragoedia.bbs.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class UserDBTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void test_select_user() {
        User user = userRepository.findById(101);
        System.out.println(user);

        user = userRepository.findByUsername("liubei");
        System.out.println(user);

        user = userRepository.findByEmail("nowcoder101@sina.com");
        System.out.println(user);
    }

    @Test
    public void insert_user() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("123456");
        user.setSalt("abc");
        user.setEmail("test@qq.com");
        user.setHeaderUrl("http://www.newcoder.com/101.png");
        user.setCreateTime(new Date());

        User save = userRepository.save(user);
        System.out.println(save);
    }

    @Test
    public void update_user() {
        int i = userRepository.updateStatusById(150, 1);
        System.out.println(i);

        i = userRepository.updateHeaderById(150, "111");
        System.out.println(i);

        i = userRepository.updatePasswordById(150, "aaaaa");
        System.out.println(i);
    }

}
