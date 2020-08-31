package com.paichi.modules.index;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

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
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("login/index");
        //判断是注册还是登陆，注册前端ac=zhuce参数
        String ac = request.getParameter("ac");
        if (!"".equals(ac)) {
            mv.addObject("ac", ac);
        }

        return mv;
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
