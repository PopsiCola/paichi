package com.paichi.modules.record.mapper;

import com.paichi.modules.record.entity.FileRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

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


    /**
     * 删除临时文件
     * @param url
     * @return
     */
    Integer deleteTempFileByUrl(String url);

    /**
     * 查询临时文件
     * @return
     */
    List<FileRecord> queryTempFile();
}
