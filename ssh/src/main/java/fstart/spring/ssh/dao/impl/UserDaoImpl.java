package fstart.spring.ssh.dao.impl;

import fstart.spring.ssh.dao.UserDao;
import fstart.spring.ssh.pojo.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * 获取与当前线程绑定的 session
     */
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public User queryUserById(Long id) {
        Query query = getSession().createQuery("from User where id = ? ");
        query.setParameter(0, id);
        return (User) query.uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> queryAllUser() {
        return getSession().createQuery("from User").list();
    }

}
