package com.aye10032.database.dao;

import com.aye10032.database.pojo.Directory;
import com.aye10032.database.pojo.History;
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
public class DaoImpl implements IHistoryDAO, IVideoDao, IDirectoryDao{

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
    public List<Video> selectWithRoot(Integer root_id) {
        List<Video> list = null;
        initSession();

        IVideoDao dao = session.getMapper(IVideoDao.class);
        list = dao.selectWithRoot(root_id);

        closeAll();
        return list;
    }

    @Override
    public List<Video> selectWithParent(Integer parent_id) {
        List<Video> list = null;
        initSession();

        IVideoDao dao = session.getMapper(IVideoDao.class);
        list = dao.selectWithParent(parent_id);

        closeAll();
        return list;
    }

    @Override
    public void setRootVideoDone(Video video) {
        initSession();
        IVideoDao dao = session.getMapper(IVideoDao.class);

        dao.setRootVideoDone(video);
        session.commit();
        closeAll();
    }

    @Override
    public void setParentVideoDone(Video video) {
        initSession();
        IVideoDao dao = session.getMapper(IVideoDao.class);

        dao.setParentVideoDone(video);
        session.commit();
        closeAll();
    }

    @Override
    public void setVideoDone(Video video) {
        initSession();
        IVideoDao dao = session.getMapper(IVideoDao.class);

        dao.setVideoDone(video);
        session.commit();
        closeAll();
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
    public List<Directory> getAllRoots() {
        List<Directory> list = null;
        initSession();

        IDirectoryDao dao = session.getMapper(IDirectoryDao.class);
        list = dao.getAllRoots();

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
    public List<Directory> selectDirectoryWithRootID(Integer id) {
        List<Directory> list = null;
        initSession();

        IDirectoryDao dao = session.getMapper(IDirectoryDao.class);
        list = dao.selectDirectoryWithRootID(id);

        closeAll();
        return list;
    }

    @Override
    public List<Directory> selectDirectoryWithParentID(Integer parent_id) {
        List<Directory> list = null;
        initSession();

        IDirectoryDao dao = session.getMapper(IDirectoryDao.class);
        list = dao.selectDirectoryWithParentID(parent_id);

        closeAll();
        return list;
    }

    @Override
    public void updateHide(Integer parent_id, boolean done) {
        initSession();
        IDirectoryDao dao = session.getMapper(IDirectoryDao.class);

        dao.updateHide(parent_id, done);
        session.commit();
        closeAll();
    }

    @Override
    public void updateRootAvailable(Directory directory) {
        initSession();
        IDirectoryDao dao = session.getMapper(IDirectoryDao.class);

        dao.updateRootAvailable(directory);
        session.commit();
        closeAll();
    }


    @Override
    public void creatHistoryTable() {
        initSession();
        IHistoryDAO dao = session.getMapper(IHistoryDAO.class);

        dao.creatHistoryTable();
        session.commit();
        closeAll();
    }

    @Override
    public Integer insert(History history) {
        initSession();
        IHistoryDAO dao = session.getMapper(IHistoryDAO.class);

        dao.insert(history);
        session.commit();
        closeAll();

        return history.getId();
    }

    @Override
    public List<History> selectHistory(Integer root_id) {
        List<History> list = null;
        initSession();
        IHistoryDAO dao = session.getMapper(IHistoryDAO.class);

        list = dao.selectHistory(root_id);
        closeAll();

        return list;
    }

    @Override
    public void updateHistory(History history) {
        initSession();
        IHistoryDAO dao = session.getMapper(IHistoryDAO.class);

        dao.updateHistory(history);
        session.commit();
        closeAll();
    }

    @Override
    public List<History> selectLastHistory() {
        List<History> list = null;
        initSession();
        IHistoryDAO dao = session.getMapper(IHistoryDAO.class);

        list = dao.selectLastHistory();
        closeAll();

        return list;
    }
}
