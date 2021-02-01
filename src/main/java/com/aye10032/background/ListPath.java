package com.aye10032.background;

import com.aye10032.database.dao.DaoImpl;
import com.aye10032.database.pojo.Directory;
import com.aye10032.database.pojo.Video;

import java.util.*;

/**
 * @program: VideoListUtil
 * @description: 文件路径工具类
 * @author: Aye10032
 * @create: 2021-01-31 18:04
 **/
public class ListPath {

    public static Set<Map.Entry<Integer, String>> getPath(Integer id) throws IndexOutOfBoundsException{
        Map<Integer, String> path = new LinkedHashMap<>();
        DaoImpl dao = new DaoImpl();

        Directory directory = dao.selectDirectoryWithID(id).get(0);
        path.put(directory.getId(), directory.getName());
        while (!directory.isIs_root()) {
            directory = dao.selectDirectoryWithID(directory.getParent_id()).get(0);
            path.put(directory.getId(), directory.getName());
        }
        path.put(-1, directory.getParent());

        ListIterator<Map.Entry<Integer, String>> iterator =
                new ArrayList<Map.Entry<Integer, String>>(path.entrySet()).listIterator(path.size());

        Map<Integer, String> result = new LinkedHashMap<>();

        while (iterator.hasPrevious()) {
            Map.Entry<Integer, String> entry = iterator.previous();
            result.put(entry.getKey(), entry.getValue());
        }
        return result.entrySet();
    }

    public static String getPathString(Integer id) {
        StringBuilder path_builder = new StringBuilder();
        Set<Map.Entry<Integer, String>> path_set = getPath(id);

        for (Map.Entry<Integer, String> entry : path_set) {
            path_builder.append(entry.getValue())
                    .append("\\");
        }

        return path_builder.substring(0, path_builder.length() - 1);
    }

    public static String getVideoPath(Integer id){
        DaoImpl dao = new DaoImpl();
        Video video = dao.selectWithID(id).get(0);

        String path = getPathString(video.getParent_id());

        path += "\\" + video.getName();

        return path;
    }
}
