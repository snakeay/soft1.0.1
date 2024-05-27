package com.se.se_part.Service.Impl;

import com.alibaba.druid.util.StringUtils;
import com.se.se_part.Dao.UserRepository;
import com.se.se_part.Entity.User;
import com.se.se_part.Service.UserService;
import com.se.se_part.Utils.JwtHelper;
import com.se.se_part.Utils.Result;
import com.se.se_part.Utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userrepository;
    @Autowired
    private JwtHelper jwtHelper;
    @Override
    public Result login(User user)
    {
        User LoginUser = new User();
        LoginUser = userrepository.FindByUserAccount(user.getAccount());
        if(LoginUser == null) //如果数据库中没有这个账号
        {
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }
        if(!StringUtils.isEmpty(LoginUser.getPassword()) && LoginUser.getPassword().equals(user.getPassword()))
        {
            String token = jwtHelper.createToken(Long.valueOf(LoginUser.getId()));
            Map data=new HashMap();
            data.put("token",token);
            return Result.ok(data);
        }
        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }

    @Override
    public Result register(User user)
    {
        User userForCheck = userrepository.FindByUserAccount(user.getAccount());
        if(userForCheck != null) //如果该用户名已经被使用
        {
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        User userCreated = userrepository.CreateNewUser(user.getAccount(),user.getPassword(),user.getNickname());
        System.out.println(userCreated);
        return Result.ok(null);

    }

}
