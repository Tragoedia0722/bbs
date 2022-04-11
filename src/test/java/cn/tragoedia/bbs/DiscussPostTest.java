package cn.tragoedia.bbs;

import cn.tragoedia.bbs.entity.DiscussPost;
import cn.tragoedia.bbs.repository.DiscussPostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class DiscussPostTest {
    @Resource
    private DiscussPostRepository discussPostRepository;

    @Test
    public void test_insert_discuss_post() {
        DiscussPost discussPost = new DiscussPost();
        discussPost.setType(1);
        discussPost.setContent("111");
        discussPost.setCreateTime(new Date());
        discussPost.setCommentCount(1);
        discussPost.setScore(10);
        discussPost.setStatus(1);
        discussPost.setTitle("title");
        discussPost.setUserId(2);
        DiscussPost save = discussPostRepository.save(discussPost);
        System.out.println(save);
    }

    @Test
    public void find_discuss_post() {
        List<DiscussPost> discussPostByUserId = discussPostRepository.findDiscussPostsByUserIdOrderByTypeDescCreateTimeDesc(1, PageRequest.of(0, 1));
        System.out.println(discussPostByUserId);

        System.out.println(discussPostRepository.findDiscussPostsByOrderByTypeDescCreateTimeDesc(PageRequest.of(0, 10)));
        System.out.println(discussPostRepository.countDiscussPostsBy());
        System.out.println(discussPostRepository.countDiscussPostsByUserId(1));
    }
}
