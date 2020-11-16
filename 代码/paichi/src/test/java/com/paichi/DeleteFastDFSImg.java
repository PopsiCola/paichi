package com.paichi;

import com.paichi.common.util.FastDFSUtils;
import com.paichi.modules.record.entity.FileRecord;
import com.paichi.modules.record.mapper.FileRecordMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author liulebin
 * @Date 2020/11/2 17:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DeleteFastDFSImg {

    private static final Logger log = LoggerFactory.getLogger(DeleteFastDFSImg.class);


    @Autowired
    private FileRecordMapper fileRecordMapper;

    @Test
    public void deletePatch() throws Exception {
        FastDFSUtils fastDFSUtils = new FastDFSUtils();

        List<FileRecord> fileRecords = fileRecordMapper.queryTempFile();
        fileRecords.forEach(file -> {
            log.info("文件记录表记录：" + file);
            //删除文件
            Boolean aBoolean = fastDFSUtils.deleteFile(file.getPictureUrl());
            if (aBoolean) {
                Integer flag = fileRecordMapper.deleteTempFileByUrl(file.getPictureUrl());
                log.info("修改文件记录表信息返回：" + flag);
                log.info("删除：" + file.getPictureUrl() + "：成功");
            } else {
                System.out.println("删除失败");
                log.error("删除:" + file.getPictureUrl() + ":失败");
            }
        });


    }
}
