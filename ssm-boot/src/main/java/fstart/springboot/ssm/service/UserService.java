package fstart.springboot.ssm.service;

import fstart.springboot.ssm.dao.UserDAO;
import fstart.springboot.ssm.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public List<User> findAllUser() {
        List<User> users = userDAO.findAllUser();
        return users;
    }

    public User findUserByName(String name) {
        User user = userDAO.findUserByName(name);
        return user;
    }

}
