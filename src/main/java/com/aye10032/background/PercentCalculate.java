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
        int[] statistic = getStatistics(parent_id);
        int done = statistic[0];
        int sum = statistic[1];

        if (sum == 0){
            return 0;
        }

        logger.info(parent_id + "已看完：" + done);
        logger.info(parent_id + "总计：" + sum);

        int percent = (int) ((done / sum) * 1000);

        logger.info(parent_id + "计算得：" + percent);

        return percent;
    }

    public static int[] getStatistics(Integer id) {
        int[] result = new int[2];
        int sum = 0;
        int done = 0;
        DaoImpl dao = new DaoImpl();

        List<Video> list = dao.selectWithParent(id);
        List<Directory> child = dao.selectDirectoryWithParentID(id);

        if (child.size() == 0) {
            for (Video video : list) {
                if (video.isHas_done()) {
                    done += 1;
                }
            }
            sum += list.size();
        } else {
            for (Directory directory : child) {
                done += getStatistics(directory.getId())[0];
                sum += getStatistics(directory.getId())[1];
            }
        }

        result[0] = done;
        result[1] = sum;

        return result;
    }

}
