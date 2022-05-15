package cn.tragoedia.bbs.service.impl;

import cn.tragoedia.bbs.entity.DiscussPost;
import cn.tragoedia.bbs.repository.DiscussPostRepository;
import cn.tragoedia.bbs.service.DiscussPostService;
import cn.tragoedia.bbs.utils.SensitiveFilter;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DiscussPostServiceImpl implements DiscussPostService {
    @Resource
    private DiscussPostRepository discussPostRepository;
    @Resource
    private SensitiveFilter sensitiveFilter;

    @Override
    public List<DiscussPost> findDiscussPostsByUserId(int userId, int page, int size) {
        return discussPostRepository.findDiscussPostsByUserIdOrderByTypeDescCreateTimeDesc(userId, PageRequest.of(page, size));
    }

    @Override
    public List<DiscussPost> findAllDiscussPosts(int page, int size) {
        return discussPostRepository.findDiscussPostsByOrderByTypeDescCreateTimeDesc(PageRequest.of(page, size));
    }

    @Override
    public DiscussPost addDiscussPost(DiscussPost discussPost) {
        if (discussPost == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        // 转义
        discussPost.setTitle(HtmlUtils.htmlEscape(discussPost.getTitle()));
        discussPost.setContent(HtmlUtils.htmlEscape(discussPost.getContent()));
        // 过滤
        discussPost.setTitle(sensitiveFilter.filter(discussPost.getTitle()));
        discussPost.setContent(sensitiveFilter.filter(discussPost.getContent()));
        return discussPostRepository.save(discussPost);
    }

    @Override
    public int countOfDiscussPostsByUserId(int id) {
        return discussPostRepository.countDiscussPostsByUserId(id);
    }

    @Override
    public int countOfDiscussPosts() {
        return discussPostRepository.countDiscussPostsBy();
    }
}
