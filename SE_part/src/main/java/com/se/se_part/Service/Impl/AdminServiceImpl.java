package com.se.se_part.Service.Impl;

import com.alibaba.druid.util.StringUtils;
import com.se.se_part.Dao.*;
import com.se.se_part.Entity.*;
import com.se.se_part.Service.AdminService;
import com.se.se_part.Service.UserService;
import com.se.se_part.Utils.JwtHelper;
import com.se.se_part.Utils.Result;
import com.se.se_part.Utils.ResultCodeEnum;
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
    private QuestionNodeRepository questionNodeRepository;
    @Autowired
    private AnswerNodeRepository answerNodeRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AdministratorRepository adminRepository;
    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public Result adminlogin(Administrator adminForLogin)
    {
        Administrator loginAdmin = new Administrator();
        loginAdmin = adminRepository.FindByAdminAccount(adminForLogin.getAccount());
        if(loginAdmin == null) //如果数据库中没有这个账号
        {
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }
        if(!StringUtils.isEmpty(loginAdmin.getPassword()) && loginAdmin.getPassword().equals(adminForLogin.getPassword()))
        {
            String token = jwtHelper.createToken(Long.valueOf(loginAdmin.getId()));
            Map data=new HashMap();
            data.put("token",token);
            return Result.ok(data);
        }
        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }

    @Override
    public Result getWholeFormDetails(Long targetFormId)
    {
        List<QuestionNode> questionNodes = questionNodeRepository.getQuestionNodeByCoreId(targetFormId); //通过问卷中心节点id找到整个问卷的所有题目
        List<Questionnaire> questionnaires = new ArrayList<>(); //用于打包整道题的List

        for(int i=0;i<questionNodes.size();i++)
        {
            Questionnaire questionnaire = new Questionnaire();
            questionnaire.setType(questionNodes.get(i).getType()); //获取第i道题的type
            questionnaire.setQuestionTitle(questionNodes.get(i).getTitle()); //获取第i道题的title

            List<String> questionContents =new ArrayList<>(); //用于打包某道题的所有选项的List
            if(questionNodes.get(i).getQ1() != "")
            {
                questionContents.add(questionNodes.get(i).getQ1());
            }
            if(questionNodes.get(i).getQ2() != "")
            {
                questionContents.add(questionNodes.get(i).getQ2());
            }
            if(questionNodes.get(i).getQ3() != "")
            {
                questionContents.add(questionNodes.get(i).getQ3());
            }
            if(questionNodes.get(i).getQ4() != "")
            {
                questionContents.add(questionNodes.get(i).getQ4());
            }
            if(questionNodes.get(i).getQ5() != "")
            {
                questionContents.add(questionNodes.get(i).getQ5());
            }
            if(questionNodes.get(i).getQ6() != "")
            {
                questionContents.add(questionNodes.get(i).getQ6());
            }
            if(questionNodes.get(i).getQ7() != "")
            {
                questionContents.add(questionNodes.get(i).getQ7());
            }
            if(questionNodes.get(i).getQ8() != "")
            {
                questionContents.add(questionNodes.get(i).getQ8());
            }
            questionnaire.setQuestionContent(questionContents); //获取第i道题的内容

            questionnaires.add(questionnaire);
        }
        return Result.ok(questionnaires);
    }

    @Override
    public Result adminDeleteForm(Long questionnaireCoreId)
    {
        List<QuestionNode> questionNodes = questionNodeRepository.getQuestionNodeByCoreId(questionnaireCoreId);
        for(int i=0;i<questionNodes.size();i++)
        {
            questionNodeRepository.deleteQuestionNode(questionNodes.get(i).getId());
        }
        questionnaireCoreRepository.deleteQuestionCore(questionnaireCoreId);
        return Result.ok(null);
    }

    @Override
    public Result getAllUsers()
    {
        List<User> allUsers = userRepository.adminGetAllUsers();
        return Result.ok(allUsers);
    }

    @Override
    public Result deleteUsers(Long userId)
    {
        userRepository.adminDelteUserById(userId);
        return Result.ok(null);
    }

    @Override
    public Result getAllGroups()
    {
        List<Group> groups = groupRepository.adminGetAllGroups();
        return Result.ok(groups);
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
        List<QuestionnaireCore> questionnaireCores = questionnaireCoreRepository.adminGetAllQuesionnaires();
        return Result.ok(questionnaireCores);
    }

    @Override
    public Result createGroup(String groupName)
    {
        Group newGroup = groupRepository.CreateNewGroup(groupName);
        if(newGroup != null)
        {
            return Result.ok(null);
        }
        return Result.ok(null);
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
            if(answerNode.getQ1() != "") answerInAnswerNode.add(answerNode.getQ1());
            if(answerNode.getQ2() != "") answerInAnswerNode.add(answerNode.getQ2());
            if(answerNode.getQ3() != "") answerInAnswerNode.add(answerNode.getQ3());
            if(answerNode.getQ4() != "") answerInAnswerNode.add(answerNode.getQ4());
            if(answerNode.getQ5() != "") answerInAnswerNode.add(answerNode.getQ5());
            if(answerNode.getQ6() != "") answerInAnswerNode.add(answerNode.getQ6());
            if(answerNode.getQ7() != "") answerInAnswerNode.add(answerNode.getQ7());
            if(answerNode.getQ8() != "") answerInAnswerNode.add(answerNode.getQ8());
            answer.setAnswer(answerInAnswerNode);
            answersList.add(answer);
        }
        return Result.ok(answersList);
    }




}
