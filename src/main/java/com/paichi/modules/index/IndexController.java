package com.paichi.modules.index;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paichi.common.util.RedisUtils;
import com.paichi.common.util.SnowflakeUtil;
import com.paichi.common.util.VerificationCodeAdapter;
import com.paichi.common.web.Message;
import com.paichi.modules.email.service.MailService;
import com.paichi.modules.user.entity.User;
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

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

}
