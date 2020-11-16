package com.paichi.modules.recipe.controller;


import com.paichi.common.web.Message;
import com.paichi.modules.recipe.entity.Taste;
import com.paichi.modules.recipe.service.ITasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 口味表 前端控制器
 * </p>
 *
 * @author llb
 * @since 2020-08-01
 */
@RestController
@RequestMapping("/taste")
public class TasteController {

    @Autowired
    private ITasteService tasteService;

    /**
     * 查询所有口味
     * @return
     */
    @PostMapping("tasteList")
    @ResponseBody
    public Message tasteList() {
        Message message = new Message();

        List<Taste> tasteList = tasteService.queryTaste();
        message.setCode(0);
        message.setData(tasteList);
        message.setMsg("查询成功");
        return message;
    }
}

