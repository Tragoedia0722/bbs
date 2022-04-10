package cn.tragoedia.bbs.repository;

import cn.tragoedia.bbs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(int id);

    User findByUsername(String username);

    User findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update User set status=?2 where id=?1")
    int updateStatusById(int id, int status);

    @Transactional
    @Modifying
    @Query("update User set headerUrl=?2 where id=?1")
    int updateHeaderById(int id, String headerUrl);

    @Transactional
    @Modifying
    @Query("update User set password =?2 where id =?1")
    int updatePasswordById(int id, String password);
}
