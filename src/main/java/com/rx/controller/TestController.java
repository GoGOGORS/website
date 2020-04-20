package com.rx.controller;

import com.nearze.core.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RuiXiang
 * 2020-04-03-11:01
 *
 * @author: RuiXiang
 */

@Slf4j
@RestController
public class TestController {

    @GetMapping("/test")
    public ResultBody test(){
        log.trace("这是trace信息");
        log.debug("这是debug信息");
        log.info("这是info信息");
        log.error("这是erro信息");
        return ResultBody.success("傻了巴巴的吧");
    }


}
