package com.ctgu.elmboot.pojo;

import lombok.Data;

/**
 * @author larry
 * @date 2020/8/3 20:05
 * @creed: Talk is cheap, show me the code!
 */

@Data
public class User {
    private String userId;
    private String password;
    private String userName;
    private int userSex;
    private int delTag;
}
