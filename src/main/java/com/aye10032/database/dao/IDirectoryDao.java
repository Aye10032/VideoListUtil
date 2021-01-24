package com.aye10032.database.dao;

import com.aye10032.database.pojo.Directory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @program: VideoListUtil
 * @description: directory_table的dao接口
 * @author: Aye10032
 * @create: 2021-01-21 16:52
 **/
public interface IDirectoryDao {

    @Update("CREATE TABLE \"directory_table\" (\n" +
            "\t\"id\"\tINTEGER NOT NULL UNIQUE,\n" +
            "\t\"name\"\tTEXT NOT NULL,\n" +
            "\t\"is_root\"\tBLOB NOT NULL,\n" +
            "\t\"parent\"\tTEXT NOT NULL,\n" +
            "\t\"parent_id\"\tINTEGER,\n" +
            "\t\"root\"\tTEXT,\n" +
            "\t\"root_id\"\tINTEGER,\n" +
            "\t\"available\"\tBLOB NOT NULL,\n" +
            "\t\"creat_date\"\tBLOB NOT NULL,\n" +
            "\t\"update_date\"\tBLOB NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
            ")")
    void creatDirectoryTable();

    @Select("SELECT * FROM directory_table")
    List<Directory> getAllDirectory();

    @Insert("INSERT INTO 'directory_table'" +
            "('name','is_root','parent','parent_id','root','root_id','available','creat_date','update_date') VALUES" +
            " (#{name}, #{is_root}, #{parent}, #{parent_id}, #{root}, #{root_id}, #{available}, #{creat_date}, #{update_date})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer insert(Directory directory);

    @Select("SELECT * FROM directory_table WHERE is_root=1 AND available=1")
    List<Directory> getRoots();

    @Select("SELECT * FROM directory_table WHERE is_root=1")
    List<Directory> getAllRoots();

    @Select("SELECT * FROM directory_table WHERE id=#{id}")
    List<Directory> selectDirectoryWithID(Integer id);

    @Select("SELECT * FROM directory_table WHERE name=#{name}")
    List<Directory> selectWithName(String name);

    @Select("SELECT * FROM directory_table WHERE parent_id=#{id}")
    List<Directory> selectWithParentID(Integer id);

    @Select("UPDATE directory_table available=#{done} WHERE id={parent_id}")
    void updateHide(Integer parent_id, boolean done);

    @Update("UPDATE directory_table SET available=#{available}, update_date=#{update_date} WHERE id=#{root_id} OR root_id=#{root_id}")
    void updateRootAvailable(Directory directory);
}
