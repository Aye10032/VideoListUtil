package com.aye10032.database.dao;

import com.aye10032.database.pojo.Video;
import org.apache.ibatis.annotations.*;

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

    @Select("SELECT * FROM video_table WHERE id=#{id}")
    List<Video> selectWithID(Integer id);

    @Select("SELECT * FROM video_table WHERE root_id=#{root_id}")
    List<Video> selectWithRoot(Integer root_id);

    @Select("SELECT * FROM video_table WHERE parent_id=#{parent_id}")
    List<Video> selectWithParent(Integer parent_id);

    @Update("UPDATE video_table SET has_done=#{has_done}, date=#{date} WHERE root_id=#{root_id}")
    void setRootVideoDone(Video video);

    @Update("UPDATE video_table SET has_done=#{has_done}, date=#{date} WHERE parent_id=#{parent_id}")
    void setParentVideoDone(Video video);

    @Update("UPDATE video_table SET has_done=#{has_done}, date=#{date} WHERE id=#{id}")
    void setVideoDone(Video video);

    @Update("UPDATE video_table SET has_done=#{has_done}, date=#{date} WHERE id<=#{id} AND parent_id=#{parent_id}")
    void setListVideoDone(Video video);

    @Delete("DELETE FROM video_table WHERE id=#{id}")
    void deleteVideoWithID(Integer id);

    @Delete("DELETE FROM video_table WHERE root_id=#{root_id}")
    void deleteVideoWithRootID(Integer root_id);

}
