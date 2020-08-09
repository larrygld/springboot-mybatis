package com.ctgu.elmboot.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author larry
 * @date 2020/8/4 22:21
 * @creed: Talk is cheap, show me the code!
 */

@Configuration
public class PageHelperConfig {
    @Bean
    public PageHelper createPaeHelper(){
        PageHelper page= new PageHelper();
        return page;
    }
}