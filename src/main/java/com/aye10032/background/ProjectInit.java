package com.aye10032.background;

import com.aye10032.database.dao.DaoImpl;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * @program: VideoListUtil
 * @description: 程序总初始化
 * @author: Aye10032
 * @create: 2021-01-25 01:09
 **/
public class ProjectInit {

    private static Logger logger = Logger.getLogger(ProjectInit.class);

    public static void init(){
        File file = new File("videolist.db");
        if (!file.exists()){
            logger.info("未找到,已创建新数据库");

            DaoImpl dao = new DaoImpl();

            dao.createVideoTable();
            dao.creatDirectoryTable();
        }else {
            logger.info("已找到数据库");
        }
    }

}
