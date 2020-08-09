package com.ctgu.elmboot.controller;

import com.ctgu.elmboot.pojo.User;
import com.ctgu.elmboot.service.UserService;
import com.ctgu.elmboot.service.UserServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author larry
 * @date 2020/8/3 20:11
 * @creed: Talk is cheap, show me the code!
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param uid
     * @param pwd
     * @return
     */
    @ResponseBody
    @RequestMapping("/login")
    public User login(String uid, String pwd){
        return userService.login(uid,pwd);
    }


    //mybatis分页查询
    @RequestMapping("/index/{page}/{limit}")
    @ResponseBody
    public Map<String, Object> index(@PathVariable("page") int page, @PathVariable("limit") int limit){
//  创建Page对象，将page，limit参数传入，必须位于从数据库查询数据的语句之前，否则不生效
        Page pages = PageHelper.startPage(page, limit);
//  ASC是根据id 正向排序，DESC是反向排序
        PageHelper.orderBy("userId ASC");
// 从数据库查询，这里返回的的allUser就已经分页成功了
        List<User> allUser = userService.getAllUser();
// 获取查询记录总数，必须位于从数据库查询数据的语句之后，否则不生效
        long total = pages.getTotal();
// 自动以分页要求的信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("data",allUser);
        map.put("count",total);
        return  map;
    }
}
