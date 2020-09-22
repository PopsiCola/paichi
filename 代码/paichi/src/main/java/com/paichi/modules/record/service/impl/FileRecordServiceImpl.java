package com.paichi.modules.record.service.impl;

import com.paichi.modules.record.entity.FileRecord;
import com.paichi.modules.record.mapper.FileRecordMapper;
import com.paichi.modules.record.service.IFileRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文件记录表 服务实现类
 * </p>
 *
 * @author llb
 * @since 2020-09-22
 */
@Service
public class FileRecordServiceImpl extends ServiceImpl<FileRecordMapper, FileRecord> implements IFileRecordService {

}
