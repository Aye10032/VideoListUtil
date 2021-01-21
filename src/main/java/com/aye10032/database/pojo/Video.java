package com.aye10032.database.pojo;

/**
 * @program: VideoListUtil
 * @description: video_table对应类
 * @author: Aye10032
 * @create: 2021-01-21 16:02
 **/
public class Video {
    private Integer id;
    private String name;
    private String parent;
    private Integer parent_id;
    private Integer has_done;
    private String md5;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public void setHas_done(Integer has_done) {
        this.has_done = has_done;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getParent() {
        return parent;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public Integer getHas_done() {
        return has_done;
    }

    public String getMd5() {
        return md5;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent='" + parent + '\'' +
                ", parent_id=" + parent_id +
                ", has_done=" + has_done +
                ", md5='" + md5 + '\'' +
                '}';
    }
}
