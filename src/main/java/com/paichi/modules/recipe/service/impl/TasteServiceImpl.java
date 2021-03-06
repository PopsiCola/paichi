package com.paichi.modules.recipe.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paichi.modules.recipe.entity.Taste;
import com.paichi.modules.recipe.mapper.TasteMapper;
import com.paichi.modules.recipe.service.ITasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 口味表 服务实现类
 * </p>
 *
 * @author llb
 * @since 2020-08-01
 */
@Service
public class TasteServiceImpl extends ServiceImpl<TasteMapper, Taste> implements ITasteService {

    @Autowired
    private TasteMapper tasteMapper;

    /**
     * 添加口味
     * @param taste
     */
    @Override
    public Integer addTaste(Taste taste) {
        //查询口味是否存在
        Taste existTaste = tasteMapper.getTaste(taste.getTasteName());
        if (existTaste == null) {
            Integer row = tasteMapper.saveTaste(taste);
            return taste.getTasteId();
        }
        return existTaste.getTasteId();
    }

    /**
     * 查询所有口味
     * @return
     */
    @Override
    public List<Taste> queryTaste() {
        return tasteMapper.queryTaste();
    }
}
