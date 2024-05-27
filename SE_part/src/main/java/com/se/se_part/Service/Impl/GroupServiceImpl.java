package com.se.se_part.Service.Impl;

import com.se.se_part.Dao.GroupRepository;
import com.se.se_part.Entity.Group;
import com.se.se_part.Service.GroupService;
import com.se.se_part.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;

public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Override
    public Result createGroup(Group group) {
        Group groupNew=groupRepository.CreateNewGroup();
        System.out.println("groupNew = " + groupNew);
        return Result.ok(null);
    }
}
