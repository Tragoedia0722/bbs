package cn.tragoedia.bbs.controller;

import cn.tragoedia.bbs.entity.DiscussPost;
import cn.tragoedia.bbs.entity.User;
import cn.tragoedia.bbs.service.DiscussPostService;
import cn.tragoedia.bbs.service.UserService;
import cn.tragoedia.bbs.utils.PageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Resource
    private DiscussPostService discussPostService;

    @Resource
    private UserService userService;

    @GetMapping({"/index", "/"})
    public String getIndexPage(Model model, PageUtil pageUtil) {
        // 获取总贴子数并计算总页数
        pageUtil.setRows(discussPostService.countOfDiscussPosts());
        pageUtil.setPath("/index");
        // 查询第一页数据
        List<DiscussPost> allDiscussPosts = discussPostService.findAllDiscussPosts(pageUtil.getCurrentPage() - 1, pageUtil.getSize()); // jpa分页从0开始
        // 构建帖子及用户列表
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        // 遍历帖子数据获取用户信息，将贴子及用户数据加入到列表中
        for (DiscussPost discussPost : allDiscussPosts) {
            Map<String, Object> map = new HashMap<>();
            map.put("post", discussPost);
            User user = userService.findUserById(discussPost.getUserId());
            map.put("user", user);
            discussPosts.add(map);
        }
        // 添加模型
        model.addAttribute("discussPosts", discussPosts);
        // 返回视图
        return "index";
    }
}
