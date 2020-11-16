package com.paichi.modules.craft.controller;


import com.paichi.common.web.Message;
import com.paichi.modules.craft.entity.Craft;
import com.paichi.modules.craft.service.ICraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 食谱工艺(食谱做法)表 前端控制器
 * </p>
 *
 * @author llb
 * @since 2020-08-01
 */
@RestController
@RequestMapping("/craft")
public class CraftController {

    @Autowired
    private ICraftService craftService;

    /**
     * 工艺种类
     * @return
     */
    @PostMapping("queryCraft")
    @ResponseBody
    public Message craftList() {
        Message message = new Message();

        List<Craft> crafts = craftService.queryCraft();
        message.setCode(0);
        message.setMsg("查询成功");
        message.setData(crafts);
        return message;
    }
}

