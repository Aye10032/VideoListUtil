package com.aye10032.database.dao;

import com.aye10032.database.pojo.Directory;
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
public class DaoImpl implements IVideoDao, IDirectoryDao{

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
    public void createVideoTable() {
        initSession();
        IVideoDao dao = session.getMapper(IVideoDao.class);

        dao.createVideoTable();
        session.commit();

        closeAll();
    }

    @Override
    public List<Video> getAllVideo() {
        List<Video> list = null;

        initSession();

        IVideoDao dao = session.getMapper(IVideoDao.class);

        list = dao.getAllVideo();

        closeAll();

        return list;
    }

    @Override
    public Integer insert(Video video) {
        initSession();
        IVideoDao dao = session.getMapper(IVideoDao.class);

        dao.insert(video);
        session.commit();

        closeAll();

        return video.getId();
    }

    @Override
    public void creatDirectoryTable() {
        initSession();
        IDirectoryDao dao = session.getMapper(IDirectoryDao.class);

        dao.creatDirectoryTable();
        session.commit();

        closeAll();
    }

    @Override
    public List<Directory> getAllDirectory() {
        return null;
    }

    @Override
    public Integer insert(Directory directory) {

        initSession();
        IDirectoryDao dao = session.getMapper(IDirectoryDao.class);

        dao.insert(directory);
        session.commit();

        closeAll();

        return directory.getId();
    }

    @Override
    public List<Directory> getRoots() {
        List<Directory> list = null;

        initSession();

        IDirectoryDao dao = session.getMapper(IDirectoryDao.class);

        list = dao.getRoots();

        closeAll();

        return list;
    }

    @Override
    public List<Directory> selectDirectoryWithID(Integer id) {
        List<Directory> list = null;

        initSession();

        IDirectoryDao dao = session.getMapper(IDirectoryDao.class);

        list = dao.selectDirectoryWithID(id);

        closeAll();

        return list;
    }

    @Override
    public List<Directory> selectWithName(String name) {
        List<Directory> list = null;

        initSession();

        IDirectoryDao dao = session.getMapper(IDirectoryDao.class);

        list = dao.selectWithName(name);

        closeAll();

        return list;
    }

    @Override
    public List<Directory> selectWithParentID(Integer id) {
        List<Directory> list = null;

        initSession();

        IDirectoryDao dao = session.getMapper(IDirectoryDao.class);

        list = dao.selectWithParentID(id);

        closeAll();

        return list;
    }


}
