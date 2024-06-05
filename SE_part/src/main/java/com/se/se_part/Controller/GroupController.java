package com.se.se_part.Controller;

import com.se.se_part.Entity.Group;
import com.se.se_part.Entity.User;
import com.se.se_part.Service.GroupService;
import com.se.se_part.Utils.JwtHelper;
import com.se.se_part.Utils.Result;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
@CrossOrigin
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private JwtHelper jwtHelper;
    @PostMapping("/createGroup")
    public Result createGroup(String token,@RequestBody Group group) //group接受前端传来的groupName
    {
        Result result= new Result();
        Long userId = jwtHelper.getUserId(token);
        String groupName = group.getGroupName();
        result =groupService.createGroup(userId, groupName);
        return result;
    }

    @PostMapping("/joinGroup")
    public Result joinGroup(String token,@RequestBody Group group) //token解析出当前用户，group接受前端传来的groupId
    {
        Result result= new Result();
        Long userId = jwtHelper.getUserId(token);
        Long groupId = group.getId();
        result =groupService.joinGroup(userId, groupId);
        return result;
    }

    @PostMapping("/exitGroup")
    public Result exitGroup(String token,@RequestBody Group group)
    {
        Result result= new Result();
        Long userId = jwtHelper.getUserId(token);
        Long groupId = group.getId();
        result = groupService.exitGroup(userId,groupId);
        return result;
    }

    @PostMapping("/administrateGroup")
    public Result administrateGroup(@RequestBody Group group, @RequestBody User user)
    {
        Result result= new Result();
        Long groupId = group.getId();
        Long userId = user.getId();
        result = groupService.adminGroup(userId,groupId);
        return result;
    }


}
