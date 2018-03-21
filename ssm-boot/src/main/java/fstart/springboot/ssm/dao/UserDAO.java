package fstart.springboot.ssm.dao;

import fstart.springboot.ssm.pojo.User;

import java.util.List;

public interface UserDAO {
    public List<User> findAllUser();

    public User findUserByName(String name);

}
