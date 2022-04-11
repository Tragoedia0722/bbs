package cn.tragoedia.bbs.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "discuss_post")
@Getter
@Setter
@ToString
public class DiscussPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "type")
    private int type;

    @Column(name = "status")
    private int status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "comment_count")
    private int commentCount;

    @Column(name = "score")
    private double score;
}
