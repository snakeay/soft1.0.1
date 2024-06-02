package com.se.se_part.Service.Impl;

import com.alibaba.druid.util.StringUtils;
import com.se.se_part.Dao.GroupRepository;
import com.se.se_part.Dao.UserRepository;
import com.se.se_part.Entity.*;
import com.se.se_part.Service.UserService;
import com.se.se_part.Utils.JwtHelper;
import com.se.se_part.Utils.Result;
import com.se.se_part.Utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userrepository;
    @Autowired
    private GroupRepository grouprepository;
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

    @Override
    public Result createNewForm(List<Questionnaire> questionnaires, String questionnaireTitle, String token, List<Long> targetGroupIds)
    {
        Long questionnairecoreId = userrepository.createQuestionnaireCore(questionnaireTitle); //首先创建问卷中心节点
        System.out.println("+++++++++++0" + questionnairecoreId);
        Long userId = jwtHelper.getUserId(token); //获取用户的id
        int gsum = targetGroupIds.size();

        for(int i=0;i<gsum;i++) //将问卷中心节点连接至用户组节点上
        {
            userrepository.questionCoreToGroup(questionnairecoreId,targetGroupIds.get(i));
            System.out.println(questionnairecoreId);
            System.out.println(targetGroupIds.get(i));
        }


        Integer sum = questionnaires.size(); //其次获取问卷总共有几道题
        for(int i=0;i<sum;i++) //从第一道题顺序处理
        {
            Questionnaire questionnaire = questionnaires.get(i);
            Long questionNodeId = userrepository.createQuestionNode(i + 1, questionnaire.getType(),questionnaire.getQuestionTitle()); //创建问题节点
            userrepository.questionNodeToCore(questionNodeId,questionnairecoreId); //将这个问题节点连接至问卷中心节点
            //System.out.println(questionNodeId);
            //System.out.println(questionnairecore.getId());
            if(questionnaire.getQuestionContent()!=null) {
                int qsum = questionnaire.getQuestionContent().size();
                    for (int j = 0; j < qsum; j++) {
                        switch (j) //问题选项作为节点属性，补充到问卷问题节点
                        {
                            case 0:
                                userrepository.addNodeAttributesQ1(questionNodeId, questionnaire.getQuestionContent().get(0));
                                break;
                            case 1:
                                userrepository.addNodeAttributesQ2(questionNodeId, questionnaire.getQuestionContent().get(1));
                                break;
                            case 2:
                                userrepository.addNodeAttributesQ3(questionNodeId, questionnaire.getQuestionContent().get(2));
                                break;
                            case 3:
                                userrepository.addNodeAttributesQ4(questionNodeId, questionnaire.getQuestionContent().get(3));
                                break;
                            case 4:
                                userrepository.addNodeAttributesQ5(questionNodeId, questionnaire.getQuestionContent().get(4));
                                break;
                            case 5:
                                userrepository.addNodeAttributesQ6(questionNodeId, questionnaire.getQuestionContent().get(5));
                                break;
                            case 6:
                                userrepository.addNodeAttributesQ7(questionNodeId, questionnaire.getQuestionContent().get(6));
                                break;
                            case 7:
                                userrepository.addNodeAttributesQ8(questionNodeId, questionnaire.getQuestionContent().get(7));
                                break;
                        }
                    }
            }
        }
        return Result.ok(null);
    }

    @Override
    public Result getGroupBelongstoInfo(String token) //通过token找到用户id，再通过用户id找到用户所属于的组
    {
        Long userId = jwtHelper.getUserId(token);
        List<Group> groupInfoList = grouprepository.findBelongGroupByUserId(userId);
        Map data=new HashMap<>();

        for(int i=0;i<groupInfoList.size();i++)
        {
            data.put(groupInfoList.get(i).getGroupName(),groupInfoList.get(i).getId());
        }
        return Result.ok(data);
    }

    @Override
    public Result createFormFindGroupAdministratedTo(String token)  //通过token找到用户id，再通过用户id找到用户所管理的组
    {
        Long userId = jwtHelper.getUserId(token);
        List<Group> groupInfoList = grouprepository.findAdministrateGroupByUserId(userId);
        Map data=new HashMap<>();

        for(int i=0;i<groupInfoList.size();i++)
        {
            data.put(groupInfoList.get(i).getGroupName(),groupInfoList.get(i).getId());
        }
        return Result.ok(data);
    }

    @Override
    public Result createAnswerForm(List<Answers> answerList, Long questionnaireCoreId, String token)
    {
        Long userId = jwtHelper.getUserId(token); //找到用户id
        String title = userrepository.getquestionnaireCore(questionnaireCoreId); //首先找到答案卷对应的问题卷中心节点
        Long answerCoreId = userrepository.createAnswerCore(title); //创建答案卷中心节点
        System.out.println("+++++++++++++++"+title);
        userrepository.answerCoreToQuestionnaireCoreId(answerCoreId,questionnaireCoreId);//将答案卷中心节点连接到问题卷中心节点
        userrepository.usertoAnswerCore(userId,answerCoreId);
        for(int i=0;i< answerList.size();i++)
        {
            Answers answers = answerList.get(i);
            Long answerNodeId = userrepository.createAnswerNode(i+1); //创建答案节点
            userrepository.answerNodeToCore(answerNodeId,answerCoreId); //将答案节点连接到答案中心节点
            for(int j=0;j<answers.getAnswer().size();j++)
            {
                switch(j) //问题选项作为节点属性，补充到问卷问题节点
                {
                    case 0: userrepository.addNodeAttributesA1(answerNodeId,answers.getAnswer().get(0)); break;
                    case 1: userrepository.addNodeAttributesA2(answerNodeId,answers.getAnswer().get(1)); break;
                    case 2: userrepository.addNodeAttributesA3(answerNodeId,answers.getAnswer().get(2)); break;
                    case 3: userrepository.addNodeAttributesA4(answerNodeId,answers.getAnswer().get(3)); break;
                    case 4: userrepository.addNodeAttributesA5(answerNodeId,answers.getAnswer().get(4)); break;
                    case 5: userrepository.addNodeAttributesA6(answerNodeId,answers.getAnswer().get(5)); break;
                    case 6: userrepository.addNodeAttributesA7(answerNodeId,answers.getAnswer().get(6)); break;
                    case 7: userrepository.addNodeAttributesA8(answerNodeId,answers.getAnswer().get(7)); break;
                }
            }
        }
        return Result.ok(null);
    }

}
