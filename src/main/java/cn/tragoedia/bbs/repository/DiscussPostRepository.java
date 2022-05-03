package cn.tragoedia.bbs.repository;

import cn.tragoedia.bbs.entity.DiscussPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussPostRepository extends JpaRepository<DiscussPost, Integer> {
    List<DiscussPost> findDiscussPostsByUserIdOrderByTypeDescCreateTimeDesc(int userId, Pageable pageable);

    List<DiscussPost> findDiscussPostsByOrderByTypeDescCreateTimeDesc(Pageable pageable);

    int countDiscussPostsBy();

    int countDiscussPostsByUserId(int userId);
}
