package com.ctgu.elmboot.service;

import com.ctgu.elmboot.dao.UserDao;
import com.ctgu.elmboot.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author larry
 * @date 2020/8/3 20:08
 * @creed: Talk is cheap, show me the code!
 */
@Service
@Transactional//添加事务
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User login(String uname, String pwd) {
        return userDao.login(uname, pwd);
    }

    @Override
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }
}
