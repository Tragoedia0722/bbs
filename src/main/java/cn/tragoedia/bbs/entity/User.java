package cn.tragoedia.bbs.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private String salt; // 密码附加位

    @Column(name = "email")
    private String email;

    @Column(name = "type")
    private int type;

    @Column(name = "status")
    private int status;

    @Column(name = "activation_code")
    private String activationCode;

    @Column(name = "header_url")
    private String headerUrl;

    @Column(name = "create_time")
    private Date createTime;

}
