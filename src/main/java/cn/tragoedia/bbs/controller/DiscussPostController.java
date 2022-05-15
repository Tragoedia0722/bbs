package cn.tragoedia.bbs.controller;

import cn.tragoedia.bbs.entity.DiscussPost;
import cn.tragoedia.bbs.entity.User;
import cn.tragoedia.bbs.service.DiscussPostService;
import cn.tragoedia.bbs.utils.HostHolder;
import cn.tragoedia.bbs.utils.Result;
import cn.tragoedia.bbs.utils.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

@Controller
@RequestMapping("/discuss")
public class DiscussPostController {
    @Resource
    private DiscussPostService discussPostService;
    @Resource
    private HostHolder hostHolder;

    @PostMapping("/add")
    @ResponseBody
    public Result addDiscussPost(String title, String content) {
        User user = hostHolder.getUser();
        if (StringUtils.isBlank(title) || StringUtils.isBlank(content)) {
            return ResultUtils.Failure("未输入必要信息");
        }
        if (user == null) {
            return ResultUtils.Failure("用户尚未登陆");
        }
        DiscussPost discussPost = new DiscussPost();
        discussPost.setTitle(title);
        discussPost.setContent(content);
        discussPost.setUserId(user.getId());
        discussPost.setCreateTime(new Date());
        discussPostService.addDiscussPost(discussPost);
        return ResultUtils.Success("发布成功");
    }
}
