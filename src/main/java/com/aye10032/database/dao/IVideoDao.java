package com.aye10032.database.dao;

import com.aye10032.database.pojo.Video;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @program: VideoListUtil
 * @description: video_table的dao接口
 * @author: Aye10032
 * @create: 2021-01-21 16:10
 **/
public interface IVideoDao {

    @Update("CREATE TABLE \"video_table\" (\n" +
            "\t\"id\"\tINTEGER NOT NULL UNIQUE,\n" +
            "\t\"name\"\tTEXT NOT NULL,\n" +
            "\t\"parent\"\tTEXT NOT NULL,\n" +
            "\t\"parent_id\"\tINTEGER NOT NULL,\n" +
            "\t\"root\"\tTEXT NOT NULL,\n" +
            "\t\"root_id\"\tINTEGER NOT NULL,\n" +
            "\t\"has_done\"\tBLOB NOT NULL,\n" +
            "\t\"md5\"\tTEXT,\n" +
            "\t\"date\"\tBLOB,\n" +
            "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
            ")")
    void createVideoTable();

    @Select("SELECT * FROM video_table")
    List<Video> getAllVideo();

    @Insert("INSERT INTO 'video_table'" +
            "('name','parent','parent_id','root','root_id','has_done','md5','date') VALUES " +
            "(#{name}, #{parent}, #{parent_id}, #{root}, #{root_id}, #{has_done}, #{md5}, #{date});")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer insert(Video video);

}
