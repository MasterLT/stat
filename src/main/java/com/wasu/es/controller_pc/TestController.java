package com.wasu.es.controller_pc;

import com.wasu.es.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by MASTER_L on 2017/12/1.
 */
@RequestMapping("test")
@Controller
public class TestController {
    @Autowired
    TestService testService;

    public void test(){
        testService.esTest();
    }
}
