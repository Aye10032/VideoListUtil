package com.aye10032.database.dao;

import com.aye10032.database.pojo.Directory;
import org.apache.ibatis.annotations.*;

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

    @Select("SELECT * FROM directory_table WHERE is_root=1 ORDER BY id ASC")
    List<Directory> getAllRoots();

    @Select("SELECT * FROM directory_table WHERE id=#{id}")
    List<Directory> selectDirectoryWithID(Integer id);

    @Select("SELECT * FROM directory_table WHERE root_id=#{id} ORDER BY id ASC")
    List<Directory> selectDirectoryWithRootID(Integer id);

    @Select("SELECT * FROM directory_table WHERE parent_id=#{parent_id} AND available=1 ORDER BY id ASC")
    List<Directory> selectDirectoryWithParentID(Integer parent_id);

    @Update("UPDATE directory_table SET available=#{available}, update_date=#{update_date} WHERE id=#{root_id} OR root_id=#{root_id}")
    void updateRootAvailable(Directory directory);

    @Update("UPDATE directory_table SET available=#{available}, update_date=#{update_date} WHERE id=#{id}")
    void updateDirectoryAvailable(Directory directory);

    @Delete("DELETE FROM directory_table WHERE id=#{id}")
    void deleteDirectoryWithID(Integer id);

    @Delete("DELETE FROM directory_table WHERE id=#{root_id} OR root_id=#{root_id}")
    void deleteDirectoryWithRootID(Integer root_id);
}
