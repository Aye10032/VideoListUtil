package com.aye10032.database.dao;

import com.aye10032.database.pojo.Video;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @program: VideoListUtil
 * @description: video_table的dao接口
 * @author: Aye10032
 * @create: 2021-01-21 16:10
 **/
public interface IVideoDao {

    @Select("SELECT * FROM video_table")
    List<Video> getAll();

}
