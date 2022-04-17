package cn.tragoedia.bbs.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "login_ticket")
@Setter
@Getter
@ToString
public class LoginTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "ticket")
    private String ticket;

    @Column(name = "status")
    private int status;

    @Column(name = "expired")
    private Date expired;

}
