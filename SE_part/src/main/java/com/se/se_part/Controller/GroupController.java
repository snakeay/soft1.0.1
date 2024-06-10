package com.se.se_part.Controller;

import com.se.se_part.Service.GroupService;
import com.se.se_part.Utils.JwtHelper;
import com.se.se_part.Utils.Result;
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
    public Result createGroup(@RequestHeader String token, @RequestParam String groupName) //group接受前端传来的groupName
    {
        System.out.println("data:" + groupName);
        Result result= new Result();
        Long userId = jwtHelper.getUserId(token);
        result =groupService.createGroup(userId, groupName);
        return result;
    }

    @PostMapping("/joinGroup")
    public Result joinGroup(@RequestHeader String token,@RequestParam Long groupId) //token解析出当前用户，group接受前端传来的groupId
    {
        System.out.println("dataid:"+ groupId);
        Result result= new Result();
        Long userId = jwtHelper.getUserId(token);
        System.out.println(userId);
        result =groupService.joinGroup(userId, groupId);
        return result;
    }

    @PostMapping("/exitGroup")
    public Result exitGroup(@RequestHeader String token,@RequestParam Long groupId)
    {
        Result result= new Result();
        Long userId = jwtHelper.getUserId(token);
        result = groupService.exitGroup(userId,groupId);
        return result;
    }

    @PostMapping("/administrateGroup")
    public Result administrateGroup(@RequestParam Long groupId, @RequestParam Long userId)
    {
        Result result= new Result();
        result = groupService.adminGroup(userId,groupId);
        return result;
    }

    @PostMapping("/findGroupCreated")
    public Result findGroupCreated(@RequestHeader String token)
    {
        Result result = new Result();
        Long userId = jwtHelper.getUserId(token);
        result = groupService.findGroupCreated(userId);
        return result;
    }

    @PostMapping("/findGroupJoined")
    public Result findGroupJoined(@RequestHeader String token)
    {
        Result result = new Result();
        Long userId = jwtHelper.getUserId(token);
        result = groupService.findGroupJoined(userId);
        return result;
    }

    @PostMapping("/displayMemberOfGroup")
    public Result displayMemberOfGroup(@RequestParam Long groupId)
    {
        Result result = new Result();
        result = groupService.displayMemberOfGroup(groupId);
        return result;
    }

    @PostMapping("/deleteGroup")
    public Result deleteGroup(@RequestParam Long groupId)
    {
        Result result = new Result();
        result = groupService.deleteGroup(groupId);
        return result;
    }


}
