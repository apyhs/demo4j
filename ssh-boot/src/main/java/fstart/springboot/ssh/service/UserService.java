package fstart.springboot.ssh.service;

import fstart.springboot.ssh.pojo.User;

import java.util.List;

public interface UserService {

    public List<User> findAllUser();

    public User findUserById(Long id);

}
