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
    private String root_name;
    private Integer root_id;
    private String parent_name;
    private Integer parent_id;
    private Date last_date;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRoot_name(String root_name) {
        this.root_name = root_name;
    }

    public void setRoot_id(Integer root_id) {
        this.root_id = root_id;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
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

    public String getRoot_name() {
        return root_name;
    }

    public Integer getRoot_id() {
        return root_id;
    }

    public String getParent_name() {
        return parent_name;
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
                ", root_name='" + root_name + '\'' +
                ", root_id=" + root_id +
                ", parent_name='" + parent_name + '\'' +
                ", parent_id=" + parent_id +
                ", last_date=" + last_date +
                '}';
    }
}
