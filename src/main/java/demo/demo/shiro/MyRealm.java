package demo.demo.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

public class MyRealm implements Realm {

    @Override
    public String getName() {
        //返回一个唯一的Realm名字
        return "myRealm";
    }

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        //判断此Realm是否支持此Token
        return authenticationToken instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("myrealm~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //得到用户名
        String username = (String)authenticationToken.getPrincipal();
        //得到密码
        String password = new String((char[])authenticationToken.getCredentials());
        if(!"zhang".equals(username)) {
            //如果用户名错误
            throw new UnknownAccountException();
        }
        if(!"123".equals(password)) {
            //如果密码错误
            throw new IncorrectCredentialsException();
        }
        //如果身份认证验证成功，返回一个AuthenticationInfo实现；
        return new SimpleAuthenticationInfo(username, password, getName());
    }

}
