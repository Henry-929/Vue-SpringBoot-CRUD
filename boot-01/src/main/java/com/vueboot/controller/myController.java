package com.vueboot.controller;

import com.vueboot.bean.Result;
import com.vueboot.bean.ResultCode;
import com.vueboot.bean.User;
import com.vueboot.service.UserService;
import com.vueboot.utils.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class myController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/")
    public Result toIndex(){
        return new Result(ResultCode.SUCCESS,"hello shiro");
    }

    @RequestMapping("/user/add")
    @RequiresRoles({"user"})
    @RequiresPermissions({"add"})
    public Result adminAdd(){
        return new Result(ResultCode.SUCCESS,"add");
    }

    @RequestMapping("/guest/selectAll")
    public Result selectAll(){
        return new Result(ResultCode.SUCCESS,"selectAll");
    }

    @RequestMapping("/admin/update")
    public Result update(){
        return new Result(ResultCode.SUCCESS,"update");
    }

    @PostMapping("/login")
    public Result login(@RequestBody Map<String,String> loginMap){
        String username = loginMap.get("username");
        String password = loginMap.get("password");

        //获取当前输入的用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的数据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
        //登录，没有异常就说明登录成功
        try {
            subject.login(usernamePasswordToken);

            String sid = (String) subject.getSession().getId();
            return new Result(ResultCode.SUCCESS,sid);
        } catch (UnknownAccountException e) {
            return new Result(ResultCode.USERNAMEPASSWORDERROR);
        }catch (IncorrectCredentialsException e){
            return new Result(ResultCode.USERNAMEPASSWORDERROR);
        }
    }

    @PostMapping("/profile")
    public Result profile(){
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principals = subject.getPrincipals();
        User currentUser = (User) principals.getPrimaryPrincipal();
        return new Result(ResultCode.SUCCESS,currentUser);
    }

    //没授权
    @RequestMapping("/authError")
    public Result unauthorized(Integer code){
        return code==1?new Result(ResultCode.UNAUTHENTICATED):new Result(ResultCode.UNAUTHORISE);
    }

    //登出
    @RequestMapping("/logout")
    public Result logout(){
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return new Result(ResultCode.SUCCESS);
    }

//    @RequestMapping("/show")
//    public String show(HttpSession session){
//        Enumeration<?> enumeration = session.getAttributeNames();
//        while (enumeration.hasMoreElements()){
//            String name = enumeration.nextElement().toString();
//            Object value = session.getAttribute(name);
//            System.out.println("<B>"+name+"</B>="+value+"<br>/n");
//        }
//        return "查看session成功";
//    }

}
