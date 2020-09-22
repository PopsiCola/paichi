package com.paichi.modules.record.mapper;

import com.paichi.modules.record.entity.FileRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 文件记录表 Mapper 接口
 * </p>
 *
 * @author llb
 * @since 2020-09-22
 */
public interface FileRecordMapper extends BaseMapper<FileRecord> {

    void saveFileRecord(FileRecord fileRecord);
}
