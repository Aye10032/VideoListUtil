package com.aye10032.background;

import com.aye10032.database.dao.DaoImpl;
import com.aye10032.database.pojo.Directory;
import com.aye10032.database.pojo.Video;

import java.util.List;

/**
 * @program: VideoListUtil
 * @description: 观看进度百分比计算类
 * @author: Aye10032
 * @create: 2021-01-21 23:39
 **/
public class PercentCalculate {

    public static int getProjectPercent(Integer root_id){
        DaoImpl dao = new DaoImpl();

        List<Video> list = dao.selectWithRoot(root_id);

        int done_count = 0;

        for (Video video:list){
            if (video.isHas_done()){
                done_count += 1;
            }
        }

        int percent = (int)(done_count / list.size())*10;
        return percent;
    }

    public static void getPercent(String parent_file){

    }

}
