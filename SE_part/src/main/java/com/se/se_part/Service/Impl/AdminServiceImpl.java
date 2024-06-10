package com.se.se_part.Service.Impl;

import com.se.se_part.Dao.*;
import com.se.se_part.Entity.*;
import com.se.se_part.Service.AdminService;
import com.se.se_part.Service.UserService;
import com.se.se_part.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private QuestionnaireCoreRepository questionnaireCoreRepository;
    @Autowired
    private AnswerNodeRepository answerNodeRepository;
    @Autowired
    private UserService userService;
    @Override
    public Result getAllUsers()
    {
        List<User> allUsers = userRepository.adminGetAllUsers();
        Map<String,Long> data = new HashMap<String,Long>();
        for(int i=0; i<allUsers.size(); i++)
        {
            data.put(allUsers.get(i).getNickname(),allUsers.get(i).getId()); //nickname和id
        }

        return Result.ok(data);
    }

    @Override
    public Result deleteUsers(List<Long> userIds)
    {
        for(int i=0; i<userIds.size(); i++)
        {
            userRepository.adminDelteUserById(userIds.get(i));
        }
        return Result.ok(null);
    }

    @Override
    public Result getAllGroups()
    {
        List<Group> groups = groupRepository.adminGetAllGroups();
        Map<String,Long> data = new HashMap<String,Long>();
        for(int i=0; i<groups.size(); i++)
        {
            data.put(groups.get(i).getGroupName(),groups.get(i).getId()); //nickname和id
        }

        return Result.ok(data);
    }

    @Override
    public Result checkGroup(Long groupId)
    {
        List<User> usersInGroup = new ArrayList<>();
        usersInGroup.add(userRepository.getadminInGroup(groupId));
        usersInGroup.addAll(userRepository.getUsersInGroup(groupId));
        return Result.ok(usersInGroup);
    }

    @Override
    public Result deleteUserFormGroup(Long userId, Long groupId)
    {
        groupRepository.ExitGroup(userId,groupId);
        return Result.ok(null);
    }

    @Override
    public Result joinUserIntoGroup(Long userId, Long groupId)
    {
        groupRepository.JoinGroup(userId, groupId);
        String groupName = groupRepository.FindByGroupId(groupId).getGroupName();
        Map data=new HashMap();
        data.put("groupName",groupName);
        return Result.ok(data);
    }

    @Override
    public Result deleteGroup(Long groupId)
    {
        groupRepository.deleteGroup(groupId);
        return Result.ok(null);
    }

    @Override
    public Result getAllquestionnaires()
    {
        Map<String,Long> data = new HashMap<String,Long>();
        List<QuestionnaireCore> questionnaireCores = questionnaireCoreRepository.adminGetAllQuesionnaires();
        for(int i=0;i<questionnaireCores.size();i++)
        {
            data.put(questionnaireCores.get(i).getTitle(),questionnaireCores.get(i).getId());
        }
        return Result.ok(data);
    }

    @Override
    public Result createGroup(String groupName)
    {
        Group newGroup = groupRepository.CreateNewGroup(groupName);
        if(newGroup != null)
        {
            return Result.ok(null);
        }
        return null;
    }

    @Override
    public Result getAnswerFormDetails(Long answerCoreId)
    {
        List<AnswerNode> answerNodes = answerNodeRepository.getAllAnswerNodesByCoreId(answerCoreId); //获取所有答案节点
        List<Answers> answersList = new ArrayList<>(); //存每一道题答案的数组
        for(int i=0;i<answerNodes.size();i++)
        {
            Answers answer = new Answers();
            AnswerNode answerNode = answerNodeRepository.getAllAnswerNodesByCoreIdAndNumber(answerCoreId,i+1); //获取序号为i+1的题目答案
            List<String> answerInAnswerNode = new ArrayList<>();
            if(answerNode.getQ1() != null) answerInAnswerNode.add(answerNode.getQ1());
            if(answerNode.getQ2() != null) answerInAnswerNode.add(answerNode.getQ2());
            if(answerNode.getQ3() != null) answerInAnswerNode.add(answerNode.getQ3());
            if(answerNode.getQ4() != null) answerInAnswerNode.add(answerNode.getQ4());
            if(answerNode.getQ5() != null) answerInAnswerNode.add(answerNode.getQ5());
            if(answerNode.getQ6() != null) answerInAnswerNode.add(answerNode.getQ6());
            if(answerNode.getQ7() != null) answerInAnswerNode.add(answerNode.getQ7());
            if(answerNode.getQ8() != null) answerInAnswerNode.add(answerNode.getQ8());
            answer.setAnswer(answerInAnswerNode);
            answersList.add(answer);
        }
        return Result.ok(answersList);
    }


}
