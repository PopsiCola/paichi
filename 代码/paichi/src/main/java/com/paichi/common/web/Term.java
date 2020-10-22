package com.paichi.common.web;

import java.io.Serializable;

/**
 * 查询食谱条件实体类
 * @Author liulebin
 * @Date 2020/10/21 21:31
 */
public class Term implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 食材
     */
    private String materialTerm;

    /**
     * 难度
     */
    private String diffcultTerm;

    /**
     * 工艺
     */
    private String craftTerm;

    /**
     * 口味
     */
    private String tasteTerm;

    /**
     * 做饭时间
     */
    private String cookTimeTerm;

    @Override
    public String toString() {
        return "Term{" +
                "materialTerm='" + materialTerm + '\'' +
                ", diffcultTerm='" + diffcultTerm + '\'' +
                ", craftTerm='" + craftTerm + '\'' +
                ", tasteTerm='" + tasteTerm + '\'' +
                ", cookTimeTerm='" + cookTimeTerm + '\'' +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMaterialTerm() {
        return materialTerm;
    }

    public void setMaterialTerm(String materialTerm) {
        this.materialTerm = materialTerm;
    }

    public String getDiffcultTerm() {
        return diffcultTerm;
    }

    public void setDiffcultTerm(String diffcultTerm) {
        this.diffcultTerm = diffcultTerm;
    }

    public String getCraftTerm() {
        return craftTerm;
    }

    public void setCraftTerm(String craftTerm) {
        this.craftTerm = craftTerm;
    }

    public String getTasteTerm() {
        return tasteTerm;
    }

    public void setTasteTerm(String tasteTerm) {
        this.tasteTerm = tasteTerm;
    }

    public String getCookTimeTerm() {
        return cookTimeTerm;
    }

    public void setCookTimeTerm(String cookTimeTerm) {
        this.cookTimeTerm = cookTimeTerm;
    }
}
