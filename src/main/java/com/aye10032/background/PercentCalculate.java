package com.aye10032.background;

import com.aye10032.database.dao.DaoImpl;
import com.aye10032.database.pojo.Directory;
import com.aye10032.database.pojo.Video;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @program: VideoListUtil
 * @description: 观看进度百分比计算类
 * @author: Aye10032
 * @create: 2021-01-21 23:39
 **/
public class PercentCalculate {

    private static Logger logger = Logger.getLogger(PercentCalculate.class);

    public static int getProjectPercent(Integer root_id) {
        DaoImpl dao = new DaoImpl();

        List<Video> list = dao.selectWithRoot(root_id);

        double done_count = 0;

        for (Video video : list) {
            if (video.isHas_done()) {
                done_count += 1;
            }
        }

        int percent = (int) ((done_count / list.size()) * 1000);

        logger.debug("已看完：" + done_count);
        logger.debug("总计：" + list.size());
        logger.debug("计算得：" + percent);

        return percent;
    }

    public static int getPercent(Integer parent_id) {
        DaoImpl dao = new DaoImpl();

        List<Video> list = dao.selectWithParent(parent_id);

        double done_count = 0;

        for (Video video : list) {
            if (video.isHas_done()) {
                done_count += 1;
            }
        }

        int percent = (int) ((done_count / list.size()) * 1000);

        logger.debug("已看完：" + done_count);
        logger.debug("总计：" + list.size());
        logger.debug("计算得：" + percent);

        return percent;
    }

}
