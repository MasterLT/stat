package com.wasu.es.controller_pc;

import com.wasu.es.service.impl.PhantomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by MASTER_L on 2017/12/7.
 */
@RequestMapping("/phantom")
@Controller
public class PhantomController {

    @Autowired
    PhantomService phantomService;

    @RequestMapping()
    @ResponseBody
    public Object phantom() {
        return phantomService.phantom();
    }
}
