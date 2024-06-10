package com.se.se_part.Service.Impl;

import com.se.se_part.Dao.GroupRepository;
import com.se.se_part.Dao.UserRepository;
import com.se.se_part.Entity.Group;
import com.se.se_part.Entity.User;
import com.se.se_part.Service.GroupService;
import com.se.se_part.Utils.JwtHelper;
import com.se.se_part.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtHelper jwtHelper;
    @Override
    public Result createGroup(Long userId, String groupName) {
        Group groupNew=groupRepository.CreateNewGroup(groupName);
        System.out.println("groupNew = " + groupNew);
        if(groupNew.getId() != null){
            Long groupId = groupNew.getId();
            groupRepository.ConnectUserToGroup(userId,groupId);
            Map data=new HashMap<>();
            data.put("groupId",groupId);
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
        Group group = groupRepository.findIsUserBelongstoGroup(userId,groupId);
        if(group == null) {
            groupRepository.JoinGroup(userId, groupId);
            String groupName = groupRepository.FindByGroupId(groupId).getGroupName();
            Map data = new HashMap();
            data.put("groupName", groupName);
            return Result.ok(data);
        }
        else  return Result.fail(null);
    }


    @Override
    public Result exitGroup(Long userId, Long groupId) {
        groupRepository.ExitGroup(userId,groupId);
        return Result.ok(null);
    }

    @Override
    public Result findGroupCreated(Long userId) {
        List<Group> ListGroupCreated = new ArrayList<>();
        ListGroupCreated = groupRepository.findGroupCreatedById(userId);
        Map data=new HashMap<>();
        data.put("groupCreated",ListGroupCreated);
        return Result.ok(data);
    }

    @Override
    public Result findGroupJoined(Long userId) {
        List<Group> ListGroupJoined = new ArrayList<>();
        ListGroupJoined = groupRepository.findGroupJoineddById(userId);
        Map data=new HashMap<>();
        data.put("groupJoined",ListGroupJoined);
        return Result.ok(data);
    }

    @Override
    public Result displayMemberOfGroup(Long groupId) {
        List<User> ListMembersOfGroup = new ArrayList<>();
        ListMembersOfGroup = userRepository.findMembersJoinGroup(groupId);
        User userGroupCreated = userRepository.findUserCreatedByGroupId(groupId);
        ListMembersOfGroup.add(0,userGroupCreated);
        for(int i = 0; i < ListMembersOfGroup.size(); i++){
            ListMembersOfGroup.get(i).setPassword("");
        }
        Map data=new HashMap<>();
        data.put("membersOfGroup",ListMembersOfGroup);
        return Result.ok(data);
    }

    @Override
    public Result deleteGroup(Long groupId) {
        groupRepository.deleteGroup(groupId);
        return Result.ok(null);
    }
}
