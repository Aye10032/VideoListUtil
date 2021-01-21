package com.aye10032.database.dao;

import com.aye10032.database.pojo.Video;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @program: VideoListUtil
 * @description: 接口实现
 * @author: Aye10032
 * @create: 2021-01-21 16:17
 **/
public class VideoDaoImpl implements IVideoDao {

    private InputStream in;
    private SqlSession session;

    private void initSession() {
        try {
            in = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            SqlSessionFactory factory = builder.build(in);
            session = factory.openSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeAll() {
        try {
            session.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Video> getAll() {
        List<Video> list = null;

        initSession();

        IVideoDao dao = session.getMapper(IVideoDao.class);

        list = dao.getAll();

        closeAll();

        return list;
    }
}
