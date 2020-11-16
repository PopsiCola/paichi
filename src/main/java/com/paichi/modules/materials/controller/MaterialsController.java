package com.paichi.modules.materials.controller;


import com.paichi.common.web.Message;
import com.paichi.modules.materials.entity.Materials;
import com.paichi.modules.materials.service.IMaterialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用料表（Materials） 前端控制器
 * </p>
 *
 * @author llb
 * @since 2020-08-01
 */
@RestController
@RequestMapping("/materials")
public class MaterialsController {

    @Autowired
    private IMaterialsService materialsService;

    /**
     * 主食材列表
     * @return
     */
    @RequestMapping(value = "queryIngredients", method = RequestMethod.POST)
    @ResponseBody
    public Message queryIngredients() {
        Message message = new Message();

        List<Map> materials = materialsService.queryMainMaterial();

        message.setCode(1);
        message.setMsg("查询成功");
        message.setData(materials);
        return message;
    }
}

