package fstart.spring.ssm.service.impl;

import fstart.spring.ssm.dao.UserDao;
import fstart.spring.ssm.pojo.User;
import fstart.spring.ssm.service.UserService;

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
