package cn.tragoedia.bbs.service.impl;

import cn.tragoedia.bbs.entity.DiscussPost;
import cn.tragoedia.bbs.repository.DiscussPostRepository;
import cn.tragoedia.bbs.service.DiscussPostService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DiscussPostServiceImpl implements DiscussPostService {
    @Resource
    private DiscussPostRepository discussPostRepository;

    @Override
    public List<DiscussPost> findDiscussPostsByUserId(int userId, int page, int size) {
        return discussPostRepository.findDiscussPostsByUserIdOrderByTypeDescCreateTimeDesc(userId, PageRequest.of(page, size));
    }

    @Override
    public List<DiscussPost> findAllDiscussPosts(int page, int size) {
        return discussPostRepository.findDiscussPostsByOrderByTypeDescCreateTimeDesc(PageRequest.of(page, size));
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
