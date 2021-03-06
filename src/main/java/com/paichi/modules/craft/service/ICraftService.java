package com.paichi.modules.craft.service;

import com.paichi.modules.craft.entity.Craft;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 食谱工艺(食谱做法)表 服务类
 * </p>
 *
 * @author llb
 * @since 2020-08-01
 */
public interface ICraftService extends IService<Craft> {

    Integer addCraft(Craft craft);

    List<Craft> queryCraft();

}
