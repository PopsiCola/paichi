package com.paichi.modules.login.controller;

import com.paichi.common.util.GenerateUserNameUtil;
import com.paichi.common.util.RedisUtils;
import com.paichi.common.util.SnowflakeUtil;
import com.paichi.common.web.Message;
import com.paichi.modules.email.service.MailService;
import com.paichi.modules.user.entity.User;
import com.paichi.modules.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

/**
 * @Author liulebin
 * @Date 2020/9/9 14:20
 */
@Controller
public class RegistController {

    @Autowired
    private IUserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 用户邮箱注册
     * @param user              用户实体类
     * @param confirmPassword   确认密码
     * @param code              邮箱验证码
     * @return
     */
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    @ResponseBody
    public Message regist(User user, String confirmPassword, String code) {
        Message message;

        //一个邮箱只能注册一次
        message = userService.emailOrNameExist(user.getUserName(), user.getUserEmail());
        if (message.getCode() == 1) {
            return message;
        }

        //参数为空验证
        if (code == null || "".equals(code)) {
            message.setMsg("验证码不能为空");
            message.setCode(1);
            return message;
        }
        if (user.getPassword().length() == 0 || !user.getPassword().equals(confirmPassword)) {
            message.setMsg("密码一致且不能为空");
            message.setCode(1);
            return message;
        }

        //验证码参数验证
        Object redisCode = redisUtils.get("email:" + user.getUserEmail() + ":code");
        if (code.equals(String.valueOf(redisCode))) {
            try {
                //补充用户信息，根据雪花算法生成主键
                user.setUserId(SnowflakeUtil.getSnowflakeId());
                user.setCreateTime(new Date());
                user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
                //判断用户昵称是否为空，为空随机生成字符
                if (user.getUserName().trim().length() == 0) {
                    user.setUserName(GenerateUserNameUtil.generateUserName());
                }
                userService.saveUser(user);
                message.setMsg("注册成功");
                message.setCode(0);
            } catch (Exception e) {
                e.printStackTrace();
                message.setMsg("注册失败");
                message.setCode(1);
            }
        } else {
            message.setMsg("验证码已过期");
            message.setCode(1);
        }
        return message;
    }

    /**
     * 用户协议页面
     * @return
     */
    @RequestMapping("regist/user_agreement")
    public String userAgreement() {
        return "user_agreement/index";
    }

    /**
     * 隐私政策页面
     * @return
     */
    @RequestMapping("regist/privacy")
    public String privacy() {
        return "privacy/index";
    }

    /**
     * 邮箱验证码发送
     * @param email 邮箱地址
     * @param type  发送邮件类型：注册、登录
     * @return
     */
    @RequestMapping(value = "/mail/sendMailCode", method = RequestMethod.POST)
    @ResponseBody
    public Message sendMailCode(String email, String type) {

        Message message = new Message();

        //对参数进行校验，避免参数异常
        if (email == null || "".equals(email)) {
            message.setCode(1);
            message.setMsg("请填写正确的邮箱");
            return message;
        }
        //发送邮件
        try {
            //采用异步来发送邮件，耗时将会减少
            Future<Integer> codeFuture = mailService.sendHtmlMail(email, type);

            while (true) {
                //查看发送邮件是否异步执行完毕
                if (codeFuture.isDone()) {
                    Integer code = codeFuture.get();
                    //将验证码保存到Redis中，且设置过期时间5分钟有效，key命名规则：email:邮箱号:code
                    Boolean saveCode = redisUtils.set("email:" + email + ":code", code, 5*60L);
                    if (saveCode) {
                        message.setCode(0);
                        message.setMsg("邮件发送成功，请注意查收");
                    } else {
                        message.setCode(1);
                        message.setMsg("Redis保存失败，请稍后重试");
                    }
                    break;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            message.setCode(1);
            message.setMsg("发送邮件失败，请稍后重试");
            return message;
        }

        return message;
    }

    /**
     * 邮箱验证码验证
     * @param email 用户邮箱
     * @param code  用户输入的验证码
     * @return
     */
    @RequestMapping(value = "regist/comfirmVerifyCode", method = RequestMethod.POST)
    @ResponseBody
    public Message comfirmVerifyCode(String email, String code) {
        Message message = new Message();

        //参数校验
        if (email == null || "".equals(email)) {
            message.setMsg("请填写正确的邮箱");
            message.setCode(1);
            return message;
        }
        if (code == null || "".equals(code)) {
            message.setMsg("请填写正确的验证码");
            message.setCode(1);
            return message;
        }

        //从redis中获取验证码验证
        Object redisCode = redisUtils.get("email:" + email + ":code");
        if (code.equals(String.valueOf(redisCode))) {
            message.setCode(0);
            message.setMsg("验证成功");
        } else {
            message.setMsg("验证码输入错误");
            message.setCode(1);
        }
        return message;
    }

    /**
     * 找回密码，发送邮箱验证链接
     * @param account       找回账号
     * @param accountType   账号类型：0.手机账号；1.邮箱账号
     * @return
     */
    @RequestMapping(value = "mail/sendResetPwd", method = RequestMethod.POST)
    @ResponseBody
    public Message resetPwd(@RequestParam("account") String account,
                            @RequestParam(value = "accountType", defaultValue = "1") int accountType,
                            HttpServletRequest request) {
        Message message = new Message();

        // 参数校验
        if (account == null || "".equals(account)) {
            message.setCode(1);
            message.setMsg("账号不能为空，请重新输入账号");
            return message;
        }

        if (accountType == 0) {
            //TODO:手机账号找回密码短信业务没有，暂时不开放
            message.setCode(1);
            message.setMsg("功能完善中，暂时停用，请使用邮箱找回密码。");
            return message;
        } else {
            //邮箱账号
            User user = userService.getUserByEmail(account);
            if (user == null) {
                message.setCode(1);
                message.setMsg("邮箱未注册，请先注册");
                return message;
            }

            // 找回密码验证token，并将邮箱信息保存到redis中，保存格式：email:token:reset_token，过期时间5分钟
            String resetToken = UUID.randomUUID().toString().replaceAll("-", "");

            // 发送邮件重置密码
            try {
                String server = "http://" + request.getServerName() + ":" + request.getServerPort() + "/regist/confirmResetPwdLink?pt=" + resetToken;
                mailService.sendHtmlMail(server, account, "找回密码", resetToken);

                Boolean saveToken = redisUtils.set("email:" + resetToken + ":reset_token", account, 5 * 60L);
                if (!saveToken) {
                    message.setCode(1);
                    message.setMsg("Redis保存失败，请稍后重试");
                    return message;
                }
                message.setCode(0);
                message.setMsg("邮件发送成功，请注意查收");
            } catch (MessagingException e) {
                e.printStackTrace();
                message.setCode(1);
                message.setMsg("邮件发送失败，请重新操作");
            }

        }

        return message;
    }

    /**
     * 找回密码链接验证
     * @param pt    验证token
     */
    @RequestMapping(value = "regist/confirmResetPwdLink", method = RequestMethod.GET)
    public ModelAndView confirmResetPwdLink(String pt) {

        ModelAndView modelAndView = new ModelAndView();

        Object email = redisUtils.get("email:" + pt + ":reset_token");
        if (email == null || "".equals(email)) {
            modelAndView.addObject("msg", "抱歉！链接错误，请检查链接是否完整或参数是否过期");
            modelAndView.setViewName("login/index");
            return modelAndView;
        }

        // 根据邮箱，重置密码
        // 将pt(paichiToken)保存到页面，等待用户重置密码提交后再次验证
        modelAndView.addObject("pt", pt);
        modelAndView.setViewName("resetpwd/reset_new_pwd");
        return modelAndView;
    }


    /**
     * 重置密码
     * @param pt            验证token
     * @param pwd           密码
     * @param confirmPwd    重置密码
     */
    @RequestMapping(value = "regist/resetPwd", method = RequestMethod.POST)
    @ResponseBody
    public Message confirmResetPwd(String pt, String pwd, String confirmPwd, HttpServletRequest request) {

        Message message = new Message();

        if (!pwd.equals(confirmPwd)) {
            message.setCode(1);
            message.setMsg("两次输入的密码不一致");
            return message;
        }

        Object email = redisUtils.get("email:" + pt + ":reset_token");

        // 校验token是否有效，防止重复提交
        if (email == null || "".equals(email)) {
            message.setCode(1);
            message.setMsg("抱歉！链接错误，请检查链接是否完整或参数是否过期");
            return message;
        }

        // 修改密码
        User user = new User();
        user.setUserEmail((String) email);
        user.setPassword(DigestUtils.md5DigestAsHex(pwd.getBytes()));

        // 修改密码
        userService.updatePassword(user);

        // 修改完密码后，删除token
        redisUtils.remove("email:" + pt + ":reset_token");

        // 根据邮箱，重置密码
        // 将pt(paichiToken)保存到页面，等待用户重置密码提交后再次验证
        message.setCode(0);
        message.setMsg("重置成功");
        return message;
    }
}
