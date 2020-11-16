package com.paichi.modules.record.service;

import com.paichi.modules.record.entity.FileRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 文件记录表 服务类
 * </p>
 *
 * @author llb
 * @since 2020-09-22
 */
public interface IFileRecordService extends IService<FileRecord> {

    void saveFile(FileRecord fileRecord);
}
