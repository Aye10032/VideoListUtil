package com.aye10032.database.pojo;

/**
 * @program: VideoListUtil
 * @description: directory_table对应类
 * @author: Aye10032
 * @create: 2021-01-21 16:44
 **/
public class Directory {
    private Integer id;
    private String name;
    private Integer is_root;
    private String parent;
    private Integer parent_id;
    private Integer available;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIs_root(Integer is_root) {
        this.is_root = is_root;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getIs_root() {
        return is_root;
    }

    public String getParent() {
        return parent;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public Integer getAvailable() {
        return available;
    }

    @Override
    public String toString() {
        return "Directory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", is_root=" + is_root +
                ", parent='" + parent + '\'' +
                ", parent_id=" + parent_id +
                ", available=" + available +
                '}';
    }
}
