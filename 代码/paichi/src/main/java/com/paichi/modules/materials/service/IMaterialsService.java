package com.paichi.modules.materials.service;

import com.paichi.modules.materials.entity.Materials;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用料表（Materials） 服务类
 * </p>
 *
 * @author llb
 * @since 2020-08-01
 */
public interface IMaterialsService extends IService<Materials> {

    void addMaterial(Materials materials);

    List<Map> queryMainMaterial();
}
