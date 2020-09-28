package com.paichi.modules.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paichi.common.util.RedisUtils;
import com.paichi.common.util.VerificationCodeAdapter;
import com.paichi.common.web.Message;
import com.paichi.modules.user.entity.User;
import com.paichi.modules.user.service.IUserService;
import com.paichi.modules.verifyImage.entity.VerificationCodePlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * 登录
 * @Author liulebin
 * @Date 2020/9/9 14:13
 */
@Controller
public class LoginController {

    @Autowired
    private IUserService userService;
    @Autowired
    private RedisUtils redisUtils;
    //文件保存地址
    @Value("${fastDFSPath}")
    private String fastDFSPath;
    @Autowired
    private VerificationCodeAdapter verificationCodeAdapter;

    /**
     * 前去登录、注册页面
     * @param ac    ac有值则说明注册页面
     * @return
     */
    @RequestMapping("/toLogin")
    public ModelAndView login(String ac) {
        ModelAndView mv = new ModelAndView("login/index");
        //判断是注册还是登陆，注册前端ac=zhuce参数
        if (!"".equals(ac)) {
            mv.addObject("ac", ac);
        }
        return mv;
    }

    /**
     * 登录
     * @param account   账号、邮箱账号
     * @param pwd       密码或邮箱验证码
     * @param type      1:账号密码登录  2：邮箱验证码登录
     * @param request   request
     * @return
     */
    @RequestMapping(value = "login/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public Message doLogin(String account, String pwd, int type, HttpServletRequest request) {
        Message message = new Message();
        //参数校验
        if (account == null || "".equals(account) || pwd == null || "".equals(pwd)) {
            message.setMsg(type == 1 ? "账号或密码不能空" : "邮箱或验证码不能为空");
            message.setCode(1);
            return message;
        }

        User user;
        if (type ==1) {
            user = userService.getUserByUserName(account);
            //判断是否已经注册
            if (user == null) {
                message.setMsg("账号不存在，请先注册");
                message.setCode(1);
            } else if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(pwd.getBytes()))) {
                message.setMsg("密码错误");
                message.setCode(1);
            } else {

                /**
                 * 保存用户信息到session，并且保存到redis，用来做用户登录限制或单点登录
                 * 用户redis信息保存格式：info:用户id:user
                 */
                request.getSession().setAttribute("user", user);
                redisUtils.set("info:" + user.getUserId() + ":user", user);

                message.setMsg("登录成功");
                message.setCode(0);
                message.setData(user);
            }

        } else {
            user = userService.getUserByEmail(account);
            Object code = redisUtils.get("email:" + account + ":code");
            if (user == null) {
                message.setMsg("邮箱不存在，请先注册");
                message.setCode(1);
            } else if (!pwd.equals(String.valueOf(code))) {
                message.setMsg("验证码错误，请重新输入");
                message.setCode(1);
            } else {
                /**
                 * 保存用户信息到session，并且保存到redis，用来做用户登录限制或单点登录
                 * 用户redis信息保存格式：info:用户id:user
                 */
                request.getSession().setAttribute("user", user);
                redisUtils.set("info:" + user.getUserId() + ":user", user);

                message.setMsg("登录成功");
                message.setCode(0);
                message.setData(user);
            }
        }
        return message;
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping(value = "/login/loginOut", method = RequestMethod.GET)
    public String loginOut(HttpServletRequest request) {
        //清除session，删除redis中保存的用户信息
        User user = (User) request.getSession().getAttribute("user");

        boolean exists = redisUtils.exists("info:" + user.getUserId() + ":user");
        if (exists) {
            redisUtils.remove("info:" + user.getUserId() + ":user");
        }

        request.getSession().removeAttribute("user");

        return "redirect:/index";
    }

    /**
     * 随机获取背景和拼图，返回json
     * @return
     */
    @RequestMapping("/login/getImgInfo")
    @ResponseBody
    public String imgInfo(){
        VerificationCodePlace vcPlace = verificationCodeAdapter.getRandomVerificationCodePlace();

        vcPlace.setBackName(fastDFSPath + vcPlace.getBackName());
        vcPlace.setMarkName(fastDFSPath + vcPlace.getMarkName());

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
}
