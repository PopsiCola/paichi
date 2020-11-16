package com.paichi.common.web;

import java.io.Serializable;

/**
 * 分页实体类
 * @Author liulebin
 * @Date 2020/10/19 21:06
 */
public class Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页
     */
    private Integer current;

    /**
     *  每页显示条数
     */
    private Integer limit;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 总条数
     */
    private Integer totals;

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotals() {
        return totals;
    }

    public void setTotals(Integer totals) {
        this.totals = totals;
    }
}
