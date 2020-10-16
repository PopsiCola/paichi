package com.paichi.modules.email.service;

import org.springframework.scheduling.annotation.Async;

import javax.mail.MessagingException;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * 邮件服务类接口
 * @Author llb
 * Date on 2020/3/9
 */
public interface MailService {

    /**
     * 发送邮件验证码
     * @param to
     * @param subject
     */
    public Map<String, Object> sendMail(String to, String subject);

    /**
     * 发送HTML邮件验证码
     * @param to
     * @param subject
     */
    public Future<Integer> sendHtmlMail(String to, String subject) throws MessagingException;

    /**
     * 发送HTML邮件验证链接
     * @param server            服务器地址
     * @param to                发送目标邮箱
     * @param subject           标题：登录、注册、找回密码
     * @param verifyUrl   验证链接
     */
    public void sendHtmlMail(String server, String to, String subject, String verifyUrl) throws MessagingException;

}
