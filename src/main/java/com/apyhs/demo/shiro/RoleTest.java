package com.apyhs.demo.shiro;

import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.util.Arrays;

public class RoleTest {

    @Test
    public void testHasRole() {
        Subject currentUser=ShiroUtil.login("classpath:shiro_role_perm.ini", "java1234", "123456");
        // Subject currentUser=ShiroUtil.login("classpath:shiro_role_perm.ini", "jack", "123");
        System.out.println(currentUser.hasRole("role1")?"有role1这个角色":"没有role1这个角色");
        boolean []results=currentUser.hasRoles(Arrays.asList("role1","role2","role3"));
        System.out.println(results[0]?"有role1这个角色":"没有role1这个角色");
        System.out.println(results[1]?"有role2这个角色":"没有role2这个角色");
        System.out.println(results[2]?"有role3这个角色":"没有role3这个角色");
        System.out.println(currentUser.hasAllRoles(Arrays.asList("role1","role2"))?"role1,role2这两个角色都有":"role1,role2这个两个角色不全有");
        currentUser.logout();
    }

    @Test
    public void testCheckRole() {
        Subject currentUser=ShiroUtil.login("classpath:shiro_role_perm.ini", "java1234", "123456");
        // Subject currentUser=ShiroUtil.login("classpath:shiro_role_perm.ini", "jack", "123");
        currentUser.checkRole("role1");
        currentUser.checkRoles(Arrays.asList("role1","role2"));
        currentUser.checkRoles("role1","role2","role3");
        currentUser.logout();
    }

}
