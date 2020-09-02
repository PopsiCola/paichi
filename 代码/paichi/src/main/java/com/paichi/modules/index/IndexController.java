package com.paichi.modules.index;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paichi.modules.user.service.IUserService;
import com.paichi.modules.utils.VerificationCodeAdapter;
import com.paichi.modules.verifyImage.entity.VerificationCodePlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author liulebin
 * @Date 2020/8/27 20:01
 */
@Controller
public class IndexController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/index")
    public String index() {
        //return "index";
        return "index";
    }

    /**
     * 前去登录、注册页面
     * @param request
     * @return
     */
    @RequestMapping("/toLogin")
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("login/index");
        //判断是注册还是登陆，注册前端ac=zhuce参数
        String ac = request.getParameter("ac");
        if (!"".equals(ac)) {
            mv.addObject("ac", ac);
        }
        return mv;
    }

    /**
     * 用户邮箱注册
     * @return
     */
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

    @RequestMapping(value = "/sendRegistCode", method = RequestMethod.POST)
    public String sendRegistCode(@RequestBody Map<String, Object> maps) {

        return "login/index";
    }

    @Value("${afterImage.location}")
    private String imgLocation;

    @RequestMapping("/getImgInfo")
    @ResponseBody
    // 随机获取背景和拼图，返回json
    public String imgInfo(){
        VerificationCodePlace vcPlace = VerificationCodeAdapter.getRandomVerificationCodePlace(imgLocation);
        ObjectMapper om = new ObjectMapper();
        String jsonResult = "";
        try {
            jsonResult = om.writeValueAsString(vcPlace);
            return jsonResult;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    @RequestMapping("/vcode")
    // 验证码主界面
    public String vCode(){
        return "vc_sample.html";
    }

    @RequestMapping("/deleteImg")
    @ResponseBody
    // 删除生成的验证码图片
    public String deleteImg(){
        return VerificationCodeAdapter.deleteAfterImage(imgLocation);
    }

}
