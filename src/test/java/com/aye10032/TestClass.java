package com.aye10032;

import com.aye10032.background.ListVideos;
import com.aye10032.database.dao.VideoDaoImpl;
import com.aye10032.database.pojo.Video;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @program: VideoListUtil
 * @description: 测试类
 * @author: Aye10032
 * @create: 2021-01-21 15:22
 **/
public class TestClass {

    @Test
    public void TestFileList() throws IOException {
        String path = "E:\\考研\\2021计算机组成原理";

        ListVideos.getList(new File(path));
    }

    @Test
    public void TestDataBase(){
        VideoDaoImpl videoDao = new VideoDaoImpl();

        List<Video> list = videoDao.getAll();

        for (Video video:list){
            System.out.println(video);
        }
    }
}
