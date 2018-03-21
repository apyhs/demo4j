package fstart.spring.ssh.service;

import fstart.spring.ssh.pojo.User;

import java.util.List;

public interface UserService {

    public User getUserById(Long id);

    public List<User> getAllUser();

}
