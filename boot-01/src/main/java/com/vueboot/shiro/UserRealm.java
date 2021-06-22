package com.vueboot.shiro;

import com.vueboot.bean.User;
import com.vueboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义Realm域
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    public void setName(String name){
        super.setName("userRealm");
    }

    @Autowired
    UserService userService;

    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("进入授权");


        //获取当前登陆用户对象
        User currentUser = (User) principalCollection.getPrimaryPrincipal();
        log.info(currentUser.toString());

        //添加角色和权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for (String role : currentUser.getRoles().split(",")) {
            info.addRole(role);
            for (String perm : currentUser.getPerms().split(",")) {
                info.addStringPermission(perm);
            }
        }
        return info;
    }

    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("进入认证");
        //获取用户输入的登陆信息
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        //数据库中获取user，验证用户名
        User user = userService.queryUserByUsername(userToken.getUsername());
        if (user == null){
            return null;//抛出异常
        }

        //验证密码
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,user.getPwd(),this.getName());

        return simpleAuthenticationInfo ;
    }
}
