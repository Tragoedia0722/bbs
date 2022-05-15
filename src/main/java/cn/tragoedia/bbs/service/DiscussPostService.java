package cn.tragoedia.bbs.service;

import cn.tragoedia.bbs.entity.DiscussPost;

import java.util.List;

public interface DiscussPostService {
    List<DiscussPost> findDiscussPostsByUserId(int id, int page, int size);

    List<DiscussPost> findAllDiscussPosts(int page, int size);

    DiscussPost addDiscussPost(DiscussPost discussPost);

    int countOfDiscussPostsByUserId(int id);

    int countOfDiscussPosts();
}
