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
    private String material;

    /**
     * 难度
     */
    private String diffcult;

    /**
     * 工艺
     */
    private String craft;

    /**
     * 口味
     */
    private String taste;

    /**
     * 做饭时间
     */
    private String cookTime;

    @Override
    public String toString() {
        return "Term{" +
                "material='" + material + '\'' +
                ", diffcult='" + diffcult + '\'' +
                ", craft='" + craft + '\'' +
                ", taste='" + taste + '\'' +
                ", cookTime='" + cookTime + '\'' +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDiffcult() {
        return diffcult;
    }

    public void setDiffcult(String diffcult) {
        this.diffcult = diffcult;
    }

    public String getCraft() {
        return craft;
    }

    public void setCraft(String craft) {
        this.craft = craft;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getCookTime() {
        return cookTime;
    }

    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }
}
