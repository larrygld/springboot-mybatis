package com.ctgu.elmboot.dao;

import com.ctgu.elmboot.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author larry
 * @date 2020/8/3 20:05
 * @creed: Talk is cheap, show me the code!
 */
public interface UserDao {
    User login(@Param("uname")String uname, @Param("pwd")String pwd);

    List<User> getAllUser();
}
