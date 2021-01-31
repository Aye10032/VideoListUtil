package com.aye10032.background;

import com.aye10032.database.dao.DaoImpl;
import com.aye10032.database.pojo.History;

import java.util.Date;
import java.util.List;

/**
 * @program: VideoListUtil
 * @description: 处理历史记录相关数据
 * @author: Aye10032
 * @create: 2021-01-31 01:05
 **/
public class ListHistory {

    public static void addHistory(Integer root_id, Integer parent_id){
        DaoImpl dao = new DaoImpl();

        History history = new History();
        history.setRoot_id(root_id);
        history.setParent_id(parent_id);
        history.setLast_date(new Date());

        List<History> histories = dao.selectHistory(root_id);
        if (histories.size() == 0){
            dao.insert(history);
        }else {
            Integer history_id = histories.get(0).getId();
            history.setId(history_id);
            dao.updateHistory(history);
        }
    }

}
