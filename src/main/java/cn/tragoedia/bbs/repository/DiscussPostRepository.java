package cn.tragoedia.bbs.repository;

import cn.tragoedia.bbs.entity.DiscussPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussPostRepository extends JpaRepository<DiscussPost, Integer> {
    /**
     * 根据userId和页数查询发布贴，置顶及时间倒序
     *
     * @param userId
     * @param pageable
     * @return
     */
    List<DiscussPost> findDiscussPostsByUserIdOrderByTypeDescCreateTimeDesc(int userId, Pageable pageable);

    /**
     * 查询所有发布贴，置顶及时间倒序
     *
     * @param pageable
     * @return
     */
    List<DiscussPost> findDiscussPostsByOrderByTypeDescCreateTimeDesc(Pageable pageable);

    /**
     * 查询所有发布贴数量
     *
     * @return
     */
    int countDiscussPostsBy();

    /**
     * 根据userId查询所有发布贴
     *
     * @param userId
     * @return
     */
    int countDiscussPostsByUserId(int userId);
}
