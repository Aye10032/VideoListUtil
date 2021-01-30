package com.aye10032.database.dao;

import org.apache.ibatis.annotations.Update;

/**
 * @program: VideoListUtil
 * @description: 历史记录数据库dao接口
 * @author: Aye10032
 * @create: 2021-01-31 00:49
 **/
public interface IHistoryDAO {

    @Update("CREATE TABLE \"history_table\" (\n" +
            "\t\"id\"\tINTEGER NOT NULL UNIQUE,\n" +
            "\t\"root_name\"\tTEXT NOT NULL,\n" +
            "\t\"root_id\"\tINTEGER NOT NULL,\n" +
            "\t\"parent_name\"\tTEXT NOT NULL,\n" +
            "\t\"parent_id\"\tINTEGER NOT NULL,\n" +
            "\t\"last_date\"\tBLOB NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
            ")")
    void creatHistoryTable();

}
