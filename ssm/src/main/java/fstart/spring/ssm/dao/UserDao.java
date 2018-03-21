package fstart.spring.ssm.dao;

import fstart.spring.ssm.pojo.User;

import java.util.List;

public interface UserDao {

    // 根据id查询用户信息
    public User queryUserById(Long id);

    public List<User> queryAllUser();

}
