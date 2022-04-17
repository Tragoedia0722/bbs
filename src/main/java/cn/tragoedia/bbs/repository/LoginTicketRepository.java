package cn.tragoedia.bbs.repository;

import cn.tragoedia.bbs.entity.LoginTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LoginTicketRepository extends JpaRepository<LoginTicket, Integer> {
    LoginTicket findLoginTicketByTicket(String ticket);

    @Transactional
    @Modifying
    @Query("update LoginTicket set status=?2 where ticket=?1")
    int updateStatusByTicket(String ticket, int status);
}
