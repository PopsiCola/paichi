package com.paichi.modules.index;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paichi.common.util.RedisUtils;
import com.paichi.common.util.VerificationCodeAdapter;
import com.paichi.common.web.Message;
import com.paichi.modules.email.service.MailService;
import com.paichi.modules.user.service.IUserService;
import com.paichi.modules.verifyImage.entity.VerificationCodePlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author liulebin
 * @Date 2020/8/27 20:01
 */
@Controller
public class IndexController {

    @Autowired
    private IUserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private RedisUtils redisUtils;

    //文件保存地址
    @Value("${afterImage.location}")
    private String imgLocation;
    //文件保存地址
    @Value("${fastDFSPath}")
    private String fastDFSPath;

    @RequestMapping("/index")
    public String index() {
        //return "index";
        return "index";
    }

/*    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public String uploadFile(@RequestParam MultipartFile file) {
        this.getClass().getClassLoader().getResource("fdfs_client.conf").getPath().replaceAll("%20"," ");
        return "index";
    }*/

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

    @RequestMapping("login/user_agreement")
    public String userAgreement() {
        return "user_agreement/index";
    }

    @RequestMapping("login/privacy")
    public String privacy() {
        return "privacy/index";
    }

    /**
     * 注册邮箱验证码发送
     * @param reg_email 注册邮箱地址
     * @return
     */
    @RequestMapping(value = "/regist/sendMailRegistCode", method = RequestMethod.POST)
    @ResponseBody
    public Message sendRegistCode(@RequestBody String reg_email) {

        Message message = new Message();

        long startTime = new Date().getTime();
        //对参数进行校验，避免参数异常
        if (reg_email == null || "".equals(reg_email)) {
            message.setCode(1);
            message.setMsg("请填写正确的邮箱");
            return message;
        }
        long start2Time ;
        //发送邮件
        try {
            //采用异步来发送邮件，耗时将会减少
            Future<Integer> codeFuture = mailService.sendHtmlMail(reg_email, "注册");
            start2Time = new Date().getTime();
            System.out.println(start2Time - startTime);

            while (true) {
                //查看发送邮件是否异步执行完毕
                if (codeFuture.isDone()) {
                    Integer code = codeFuture.get();
                    //将验证码保存到Redis中，且设置过期时间5分钟有效，key命名规则：email:邮箱号:code
                    Boolean saveCode = redisUtils.set("email:" + reg_email + ":code", code, 5*60L);
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

        long endTime = new Date().getTime();
        System.out.println(endTime - start2Time);
        return message;
    }

    /**
     * 随机获取背景和拼图，返回json
     * @return
     */
    @RequestMapping("/login/getImgInfo")
    @ResponseBody
    public String imgInfo(){
        VerificationCodePlace vcPlace = VerificationCodeAdapter.getRandomVerificationCodePlace(imgLocation);

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
