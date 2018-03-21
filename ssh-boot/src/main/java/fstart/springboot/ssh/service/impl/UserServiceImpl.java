package fstart.springboot.ssh.service.impl;

import fstart.springboot.ssh.dao.UserDAO;
import fstart.springboot.ssh.pojo.User;
import fstart.springboot.ssh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public List<User> findAllUser() {
        List<User> users = userDAO.findAll();
        return users;
    }

    @Override
    public User findUserById(Long id) {
        User user = userDAO.findOne(id);
        return user;
    }

}
