package com.paichi.modules.materials.service.impl;

import com.paichi.modules.materials.entity.Materials;
import com.paichi.modules.materials.mapper.MaterialsMapper;
import com.paichi.modules.materials.service.IMaterialsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
