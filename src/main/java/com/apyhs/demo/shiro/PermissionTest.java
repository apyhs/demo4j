package com.apyhs.demo.shiro;

import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class PermissionTest {

    @Test
    public void testIsPermitted() {
        Subject currentUser=ShiroUtil.login("classpath:shiro_role_perm.ini", "java1234", "123456");
        // Subject currentUser=ShiroUtil.login("classpath:shiro_role_perm.ini", "jack", "123");
        System.out.println(currentUser.isPermitted("user:select")?"有user:select这个权限":"没有user:select这个权限");
        System.out.println(currentUser.isPermitted("user:update")?"有user:update这个权限":"没有user:update这个权限");
        boolean results[]=currentUser.isPermitted("user:select","user:update","user:delete");
        System.out.println(results[0]?"有user:select这个权限":"没有user:select这个权限");
        System.out.println(results[1]?"有user:update这个权限":"没有user:update这个权限");
        System.out.println(results[2]?"有user:delete这个权限":"没有user:delete这个权限");
        System.out.println(currentUser.isPermittedAll("user:select","user:update")?"有user:select,update这两个权限":"user:select,update这两个权限不全有");
        currentUser.logout();
    }

    @Test
    public void testCheckPermitted() {
//        Subject currentUser=ShiroUtil.login("classpath:shiro_role_perm.ini", "java1234", "123456");
         Subject currentUser=ShiroUtil.login("classpath:shiro_role_perm.ini", "jack", "123");
        currentUser.checkPermission("user:select");
        currentUser.checkPermissions("user:select","user:update","user:delete");
        currentUser.logout();
    }

}
