package com.aye10032.database.dao;

import com.aye10032.database.pojo.History;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @program: VideoListUtil
 * @description: 历史记录数据库dao接口
 * @author: Aye10032
 * @create: 2021-01-31 00:49
 **/
public interface IHistoryDAO {

    @Update("CREATE TABLE \"history_table\" (\n" +
            "\t\"id\"\tINTEGER NOT NULL UNIQUE,\n" +
            "\t\"root_id\"\tINTEGER NOT NULL,\n" +
            "\t\"parent_id\"\tINTEGER NOT NULL,\n" +
            "\t\"last_date\"\tBLOB NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
            ")")
    void creatHistoryTable();

    @Insert("INSERT INTO 'history_table' " +
            "('root_id', 'parent_id', 'last_date') VALUES " +
            "(#{root_id}, #{parent_id}, #{last_date})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer insert(History history);

    @Select("SELECT * FROM history_table WHERE root_id=#{root_id}")
    List<History> selectHistory(Integer root_id);

    @Update("UPDATE history_table SET parent_id=#{parent_id}, last_date=#{last_date} WHERE id=#{id}")
    void updateHistory(History history);

    @Select("SELECT * FROM history_table ORDER BY last_date DESC LIMIT 6")
    List<History> selectLastHistory();

    @Delete("DELETE FROM history_table WHERE root_id=#{root_id}")
    void deleteHistoryRoot(Integer root_id);
}
