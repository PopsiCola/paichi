package com.paichi.modules.record.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文件记录表
 * </p>
 *
 * @author llb
 * @since 2020-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("FILE_RECORD")
public class FileRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件表主键
     */
    @TableId("PICTURE_URL")
    private String pictureUrl;

    /**
     * 文件长传时间
     */
    @TableField("UPLOAD_TIME")
    private Date uploadTime;

    /**
     * 文件类型（1 长久文件  2  临时文件）
     */
    @TableField("PICTURE_TYPE")
    private Integer pictureType;


}
