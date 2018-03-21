package fstart.spring.ssm.service;

import fstart.spring.ssm.pojo.User;

import java.util.List;

public interface UserService {

    public User getUserById(Long id);

    public List<User> getAllUser();

}
