package com.paichi.modules.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author liulebin
 * @Date 2020/8/27 20:01
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index() {
        return "index/index";
    }
}
