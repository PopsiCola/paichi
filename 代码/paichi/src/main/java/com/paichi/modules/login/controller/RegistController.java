package com.paichi.modules.login.controller;

import com.paichi.common.util.RedisUtils;
import com.paichi.common.util.SnowflakeUtil;
import com.paichi.common.web.Message;
import com.paichi.modules.email.service.MailService;
import com.paichi.modules.user.entity.User;
import com.paichi.modules.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
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
     * @param type  发送邮件类型：注册、登录、找回密码
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
}
