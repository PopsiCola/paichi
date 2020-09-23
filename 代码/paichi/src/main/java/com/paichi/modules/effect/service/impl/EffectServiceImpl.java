package com.paichi.modules.effect.service.impl;

import com.paichi.modules.effect.entity.Effect;
import com.paichi.modules.effect.mapper.EffectMapper;
import com.paichi.modules.effect.service.IEffectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 功效表 服务实现类
 * </p>
 *
 * @author llb
 * @since 2020-09-20
 */
@Service
public class EffectServiceImpl extends ServiceImpl<EffectMapper, Effect> implements IEffectService {

    @Autowired
    private EffectMapper effectMapper;

    /**
     * 添加功效
     * @param effect
     */
    @Override
    public Integer addEffect(Effect effect) {
        Effect existEffect = effectMapper.getEffect(effect.getEffectName());
        //功效是否存在
        if (existEffect == null) {
            Integer row = effectMapper.saveEffect(effect);
            return effect.getEffectId();
        }
        return existEffect.getEffectId();
    }
}
