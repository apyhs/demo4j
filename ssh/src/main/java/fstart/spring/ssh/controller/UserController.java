package fstart.spring.ssh.controller;

import com.alibaba.fastjson.JSON;
import fstart.spring.ssh.pojo.User;
import fstart.spring.ssh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.Charset;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/user/{uid}", method=RequestMethod.GET)
    public ModelAndView queryUserById(@PathVariable("uid") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        if (id > 0L) {
            User user = userService.getUserById(id);
            modelAndView.addObject("msg", user);
        }
        modelAndView.setViewName("hello");
        return modelAndView;
    }

    @RequestMapping(value="/user1/{uid}", method=RequestMethod.GET)
    public ResponseEntity<String> queryUserById1(@PathVariable("uid") Long id) {
        String result = "nothing ... ";
        if (id > 0L) {
            User user = userService.getUserById(id);
            result = user != null ? JSON.toJSONString(user) : result;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value="/users", method=RequestMethod.GET)
    public List<User> findAllUser() {
        List<User> users = userService.getAllUser();
        return users;
    }

}
