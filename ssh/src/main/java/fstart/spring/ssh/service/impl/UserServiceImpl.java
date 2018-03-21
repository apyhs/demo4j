package fstart.spring.ssh.service.impl;

import fstart.spring.ssh.dao.UserDao;
import fstart.spring.ssh.pojo.User;
import fstart.spring.ssh.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Long id) {
        return userDao.queryUserById(id);
    }

    @Override
    public List<User> getAllUser() {
        return userDao.queryAllUser();
    }

}
