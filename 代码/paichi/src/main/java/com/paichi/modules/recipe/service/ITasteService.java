package com.paichi.modules.recipe.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.paichi.modules.recipe.entity.Taste;

import java.util.List;

/**
 * <p>
 * 口味表 服务类
 * </p>
 *
 * @author llb
 * @since 2020-08-01
 */
public interface ITasteService extends IService<Taste> {

    Integer addTaste(Taste taste);

    List<Taste> queryTaste();
}
