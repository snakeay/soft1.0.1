package com.se.se_part.Service.Impl;

import com.alibaba.druid.util.StringUtils;
import com.se.se_part.Dao.*;
import com.se.se_part.Entity.*;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userrepository;
    @Autowired
    private GroupRepository grouprepository;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private QuestionnaireCoreRepository questionnairecorerepository;
    @Autowired
    private QuestionNodeRepository questionnoderepository;
    @Autowired
    private AnswerCoreRepository answercorerepository;
    @Autowired
    private AnswerNodeRepository answernoderepository;

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
        questionnairecorerepository.creatorToQuestionnaireCore(userId,questionnairecoreId); //通过用户id将问题卷创建者和问题卷中心节点连接在一起

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
    public Result createAnswerForm(List<Answers> answerList, Long questionnaireCoreId, String token) //创建答案表单
    {
        Long userId = jwtHelper.getUserId(token); //找到用户id
        String title = userrepository.getquestionnaireCore(questionnaireCoreId); //首先找到答案卷对应的问题卷中心节点
        Long answerCoreId = userrepository.createAnswerCore(title); //创建答案卷中心节点
        //System.out.println("+++++++++++++++"+title);
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


    @Override
    public Result getAllFormIdAndTitle(String token) //获取所有表单的和标题
    {
        Long userId = jwtHelper.getUserId(token);
        List<Group> groups = grouprepository.findBelongGroupByUserId(userId);
        groups.addAll(grouprepository.findBelongGroupByUserId(userId)); //找到了用户所属于和所管理的所有的组
        Map data = new HashMap<>();
        for(int i=0;i<groups.size();i++)
        {
            Long groupId = groups.get(i).getId();
            List<QuestionnaireCore> questionnaireCores = questionnairecorerepository.findQuestionnaireCore(groupId);
            for(int j=0;j<questionnaireCores.size();j++)
            {
                data.put(questionnaireCores.get(j).getTitle(),questionnaireCores.get(j).getId());
            }
        }
        return Result.ok(data);
    }

    @Override
    public Result getWholeFormDetails(Long targetFormId)
    {
        List<QuestionNode> questionNodes = questionnoderepository.getQuestionNodeByCoreId(targetFormId); //通过问卷中心节点id找到整个问卷的所有题目
        List<Questionnaire> questionnaires = new ArrayList<>(); //用于打包整道题的List

        for(int i=0;i<questionNodes.size();i++)
        {
            Questionnaire questionnaire = new Questionnaire();
            questionnaire.setType(questionNodes.get(i).getType()); //获取第i道题的type
            questionnaire.setQuestionTitle(questionNodes.get(i).getTitle()); //获取第i道题的title

            List<String> questionContents =new ArrayList<>(); //用于打包某道题的所有选项的List
            if(questionNodes.get(i).getQ1() != null)
            {
                questionContents.add(questionNodes.get(i).getQ1());
            }
            if(questionNodes.get(i).getQ2() != null)
            {
                questionContents.add(questionNodes.get(i).getQ2());
            }
            if(questionNodes.get(i).getQ3() != null)
            {
                questionContents.add(questionNodes.get(i).getQ3());
            }
            if(questionNodes.get(i).getQ4() != null)
            {
                questionContents.add(questionNodes.get(i).getQ4());
            }
            if(questionNodes.get(i).getQ5() != null)
            {
                questionContents.add(questionNodes.get(i).getQ5());
            }
            if(questionNodes.get(i).getQ6() != null)
            {
                questionContents.add(questionNodes.get(i).getQ6());
            }
            if(questionNodes.get(i).getQ7() != null)
            {
                questionContents.add(questionNodes.get(i).getQ7());
            }
            if(questionNodes.get(i).getQ8() != null)
            {
                questionContents.add(questionNodes.get(i).getQ8());
            }
            questionnaire.setQuestionContent(questionContents); //获取第i道题的内容

            questionnaires.add(questionnaire);
        }
        return Result.ok(questionnaires);
    }

    @Override
    public Result getAnswerFormDetails(String token, Long questionCoreId)
    {
        Long userId = jwtHelper.getUserId(token); //获取用户id
        Long answerCoreId = answercorerepository.getAnswerCore(userId, questionCoreId); //获取答案卷中心节点id
        List<AnswerNode> answerNodes = answernoderepository.getAllAnswerNodesByCoreId(answerCoreId); //获取所有答案节点
        List<Answers> answersList = new ArrayList<>(); //存每一道题答案的数组
        for(int i=0;i<answerNodes.size();i++)
        {
            Answers answer = new Answers();
            AnswerNode answerNode = answernoderepository.getAllAnswerNodesByCoreIdAndNumber(answerCoreId,i+1); //获取序号为i+1的题目答案
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

    @Override
    public Result getAllAnswerFormIdAndFiller(Long questionCoreId)
    {
        Map data = new HashMap(); //key:填写者nickname，value:答案卷中心节点id
        List<AnswerCore> answerCoresOfOneQueCore = answercorerepository.getAllAnswerCoreByQueCoreId(questionCoreId); //找到指定问题卷的所有答案中心节点
        for(int i=0;i<answerCoresOfOneQueCore.size();i++)
        {
            String userNickname = userrepository.getFillerNickname(answerCoresOfOneQueCore.get(i).getId());
            data.put(userNickname,answerCoresOfOneQueCore.get(i).getId());
        }
        return Result.ok(data);
    }

    @Override
    public Result searchInfoByOneType(Long questionCoreId, String type)
    {
        Map data = new HashMap();
        List<AnswerCore> answerCores = answercorerepository.getAllAnswerCoreByQueCoreId(questionCoreId);
        int answerCoreNumber = questionnoderepository.getNumberByTitle(questionCoreId,type);
        for(int i=0;i<answerCores.size();i++)
        {
            Long answerCoreId = answerCores.get(i).getId();
            Answers answer = new Answers();
            String userNickname = userrepository.getFillerNickname(answerCoreId);
            AnswerNode answerNode = answernoderepository.getAnswerNodeByNumber(answerCoreId, answerCoreNumber);
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
            data.put(userNickname, answer);
        }
        System.out.println(data);

        return null;
    }

    @Override
    public Result getUserInfo(String token)
    {
        boolean expiration = jwtHelper.isExpiration(token);
        System.out.println(expiration);
        if (expiration) {
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }
        Long userId = jwtHelper.getUserId(token);
        User user = userrepository.FindByUserId(userId);
        user.setPassword("");
        System.out.println(user);
        Map data=new HashMap<>();
        data.put("loginUser",user);
        return Result.ok(data);
    }

    @Override
    public Result getCreatedFormTitleAndId(String token)
    {
        Long userId = jwtHelper.getUserId(token);
        List<QuestionnaireCore> questionnaireCores = questionnairecorerepository.getAllCreatedQuestionnaireCore(userId);
        Map data = new HashMap<>();
        for(int i=0;i<questionnaireCores.size();i++)
        {
            data.put(questionnaireCores.get(i).getTitle(),questionnaireCores.get(i).getId());
        }
        return Result.ok(data);
    }

    @Override
    public Result getFinishedFormTitleAndId(String token)
    {
        Long userId = jwtHelper.getUserId(token);
        List<AnswerCore> answerCores = new ArrayList<>();
        answerCores.addAll(answercorerepository.getAnswerCoreByUserId(userId)); //找到用户填写的所有答案中心节点
        System.out.println(answerCores);

        Map data = new HashMap<>();
        for(int i=0;i<answerCores.size();i++)
        {
            Long ansId = answerCores.get(i).getId();
            Long queId = questionnairecorerepository.getQueCoreIdByAnsId(ansId);
            data.put(answerCores.get(i).getTitle(),queId);
        }

        //System.out.println(data);

        return Result.ok(data);
    }

    @Override
    public Result getNotFinishedFormTitleAndId(String token)
    {
        Long userId = jwtHelper.getUserId(token);
        List<Group> groups = grouprepository.findBelongGroupByUserId(userId);
        groups.addAll(grouprepository.findAdministrateGroupByUserId(userId)); //找到与用户有关的所有组
        List<QuestionnaireCore> questionnaireCores = new ArrayList<>();
        System.out.println(questionnaireCores);
        List<AnswerCore> answerCores = new ArrayList<>();
        Map data = new HashMap<>();
        for(int i=0;i<groups.size();i++)
        {
            questionnaireCores.addAll(questionnairecorerepository.findQuestionnaireCore(groups.get(i).getId())); //找到和用户有关的所有问题卷中心节点
        }
        questionnaireCores.removeAll(questionnairecorerepository.getAllCreatedQuestionnaireCore(userId));
        for(int i=0;i<questionnaireCores.size();i++)
        {

            AnswerCore answerCoreTemp = answercorerepository.getAnswerCoreByRelationsAndQueId(userId, questionnaireCores.get(i).getId());
            if(answerCoreTemp == null)
            {
                data.put(questionnaireCores.get(i).getTitle(), questionnaireCores.get(i).getId());
            }
        }
        //System.out.println(data);

        return Result.ok(data);
    }
}
