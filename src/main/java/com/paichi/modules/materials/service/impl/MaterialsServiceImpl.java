package com.paichi.modules.materials.service.impl;

import com.paichi.modules.materials.entity.Materials;
import com.paichi.modules.materials.mapper.MaterialsMapper;
import com.paichi.modules.materials.service.IMaterialsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用料表（Materials） 服务实现类
 * </p>
 *
 * @author llb
 * @since 2020-08-01
 */
@Service
public class MaterialsServiceImpl extends ServiceImpl<MaterialsMapper, Materials> implements IMaterialsService {

    @Autowired
    private MaterialsMapper materialsMapper;

    /**
     * 添加用料
     * @param materials
     */
    @Override
    public void addMaterial(Materials materials) {
        materialsMapper.saveMaterial(materials);
    }

    /**
     * 查询主料食材
     * @return
     */
    @Override
    public List<Map> queryMainMaterial() {
        return materialsMapper.queryMainMaterial();
    }
}
