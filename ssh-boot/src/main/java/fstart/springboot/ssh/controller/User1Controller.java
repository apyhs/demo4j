package fstart.springboot.ssh.controller;

import fstart.springboot.ssh.pojo.User;
import fstart.springboot.ssh.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/user1")
public class User1Controller {

    private Logger logger = Logger.getLogger(User1Controller.class);

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> allUsers() {
        List<User> users = userService.findAllUser();
        if(users != null){
            logger.info("users size is " + users.size() + ".");
        }
        return users;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public User userByName(@PathVariable Long id) {
        User user = userService.findUserById(id);
        if(user != null){
            logger.info("user is " + user + ".");
        }
        return user;
    }

}
