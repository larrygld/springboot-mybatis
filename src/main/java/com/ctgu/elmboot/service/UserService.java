package com.ctgu.elmboot.service;

import com.ctgu.elmboot.pojo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author larry
 * @date 2020/8/3 20:07
 * @creed: Talk is cheap, show me the code!
 */
@Service
public interface UserService {
    User login(String uname, String pwd);
    List<User> getAllUser();
}
