package com.se.se_part.Service.Impl;

import com.se.se_part.Dao.GroupRepository;
import com.se.se_part.Entity.Group;
import com.se.se_part.Service.GroupService;
import com.se.se_part.Utils.JwtHelper;
import com.se.se_part.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private JwtHelper jwtHelper;
    @Override
    public Result createGroup(Long userId, String groupName) {
        Group groupNew=groupRepository.CreateNewGroup(groupName);
        System.out.println("groupNew = " + groupNew);
        if(groupNew.getId() != null){
            Long groupId = groupNew.getId();
            //将用户与其创建的用户组以管理关系相连
            groupRepository.ConnectUserToGroup(userId,groupId);
            String token = jwtHelper.createToken(Long.valueOf(groupNew.getId()));
            Map data=new HashMap();
            data.put("token",token);
            return Result.ok(data);
        }
        return Result.fail(null);
    }

    @Override
    public Result adminGroup(Long userId, Long groupId) {
        groupRepository.deleteGroupUser(userId, groupId);
        return Result.ok(null);
    }

    @Override
    public Result joinGroup(Long userId,Long groupId) {
        groupRepository.JoinGroup(userId, groupId);
        return Result.ok(null);
    }

    @Override
    public Result exitGroup(Long userId, Long groupId) {
        groupRepository.ExitGroup(userId,groupId);
        return Result.ok(null);
    }
}
