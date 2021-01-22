package com.aye10032.database.pojo;

import java.util.Date;

/**
 * @program: VideoListUtil
 * @description: directory_table对应类
 * @author: Aye10032
 * @create: 2021-01-21 16:44
 **/
public class Directory {
    private Integer id;
    private String name;
    private boolean is_root;
    private String parent;
    private Integer parent_id;
    private String root;
    private Integer root_id;
    private boolean available;
    private Date creat_date;
    private Date update_date;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIs_root(boolean is_root) {
        this.is_root = is_root;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public void setRoot_id(Integer root_id) {
        this.root_id = root_id;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setCreat_date(Date creat_date) {
        this.creat_date = creat_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isIs_root() {
        return is_root;
    }

    public String getParent() {
        return parent;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public String getRoot() {
        return root;
    }

    public Integer getRoot_id() {
        return root_id;
    }

    public boolean isAvailable() {
        return available;
    }

    public Date getCreat_date() {
        return creat_date;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    @Override
    public String toString() {
        return "Directory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", is_root=" + is_root +
                ", parent='" + parent + '\'' +
                ", parent_id=" + parent_id +
                ", root='" + root + '\'' +
                ", root_id='" + root_id + '\'' +
                ", available=" + available +
                ", creat_date=" + creat_date +
                ", update_date=" + update_date +
                '}';
    }
}
