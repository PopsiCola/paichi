package com.paichi.modules.materials.mapper;

import com.paichi.modules.materials.entity.Materials;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用料表（Materials） Mapper 接口
 * </p>
 *
 * @author llb
 * @since 2020-08-01
 */
public interface MaterialsMapper extends BaseMapper<Materials> {

    void saveMaterial(Materials materials);

    /**
     * 查询主食材，且出现次数多到少排序
     * @return
     */
    List<Map> queryMainMaterial();
}
