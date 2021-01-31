package com.aye10032.database.pojo;

import java.util.Date;

/**
 * @program: VideoListUtil
 * @description: 历史记录构造类
 * @author: Aye10032
 * @create: 2021-01-31 00:50
 **/
public class History {
    private Integer id;
    private Integer root_id;
    private Integer parent_id;
    private Date last_date;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRoot_id(Integer root_id) {
        this.root_id = root_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public void setLast_date(Date last_date) {
        this.last_date = last_date;
    }

    public Integer getId() {
        return id;
    }

    public Integer getRoot_id() {
        return root_id;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public Date getLast_date() {
        return last_date;
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", root_id=" + root_id +
                ", parent_id=" + parent_id +
                ", last_date=" + last_date +
                '}';
    }
}
