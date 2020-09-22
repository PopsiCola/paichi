package com.paichi.modules.craft.service.impl;

import com.paichi.modules.craft.entity.Craft;
import com.paichi.modules.craft.mapper.CraftMapper;
import com.paichi.modules.craft.service.ICraftService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 食谱工艺(食谱做法)表 服务实现类
 * </p>
 *
 * @author llb
 * @since 2020-08-01
 */
@Service
public class CraftServiceImpl extends ServiceImpl<CraftMapper, Craft> implements ICraftService {

    @Autowired
    private CraftMapper craftMapper;

    /**
     * 添加工艺
     * @param craft
     */
    @Override
    public Integer addCraft(Craft craft) {
        Craft existCraft = craftMapper.getCraft(craft.getCraftName());
        if (existCraft == null) {
            return craftMapper.saveCraft(craft);
        }
        return existCraft.getCraftId();
    }
}
