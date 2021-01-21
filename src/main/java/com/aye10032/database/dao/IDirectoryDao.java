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
            "\t\"is_root\"\tINTEGER NOT NULL,\n" +
            "\t\"parent\"\tTEXT NOT NULL,\n" +
            "\t\"parent_id\"\tINTEGER,\n" +
            "\t\"available\"\tINTEGER NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
            ")")
    void creatDirectoryTable();

    @Select("SELECT * FROM directory_table")
    List<Directory> getAllDirectory();

    @Insert("INSERT INTO 'directory_table'('name','is_root','parent','parent_id','available') VALUES" +
            " (#{name}, #{is_root}, #{parent}, #{parent_id}, #{available})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer insert(Directory directory);

    @Select("SELECT * FROM directory_table WHERE is_root=1")
    List<Directory> getRoots();

    @Select("SELECT * FROM directory_table WHERE name=#{name}")
    List<Directory> selectWithName(String name);
}
