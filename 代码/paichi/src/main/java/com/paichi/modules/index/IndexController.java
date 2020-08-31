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
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login/index";
    }

    @RequestMapping("/regist")
    public String regist() {
        return "login/index";
    }

    @RequestMapping("/user_agreement")
    public String userAgreement() {
        return "user_agreement/index";
    }

    @RequestMapping("/privacy")
    public String privacy() {
        return "privacy/index";
    }

}
