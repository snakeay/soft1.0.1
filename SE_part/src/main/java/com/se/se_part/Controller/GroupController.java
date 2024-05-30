package com.se.se_part.Controller;

import com.se.se_part.Entity.Group;
import com.se.se_part.Service.GroupService;
import com.se.se_part.Utils.Result;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
@CrossOrigin
public class GroupController {
    @Autowired
    private GroupService groupService;
    @PostMapping("/createGroup")
    public Result createGroup(Group group)
    {
        Result result= new Result();
        result =groupService.createGroup(group);
        return result;
    }
}
