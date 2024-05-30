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
                QuestionnaireCore questionnairecore = userrepository.createQuestionnaireCore(questionnaireTitle); //首先创建问卷中心节点
                Long userId = jwtHelper.getUserId(token); //获取用户的id
                int gsum = targetGroupIds.size();
                int gnum = 0;
                while(gnum!=gsum - 1) //将问卷中心节点连接至用户组节点上
                {
                    userrepository.questionCoreToGroup(questionnairecore.getId(),targetGroupIds.get(gnum));
                    gnum = gnum + 1;
                }

                Integer sum = questionnaires.size(); //其次获取问卷总共有几道题
                int num = 0;
                while(num != sum - 1) //从第一道题顺序处理
                {
                    Questionnaire questionnaire = questionnaires.get(num);
                    Long questionNodeId = userrepository.createQuestionNode(questionnaire.getType(),questionnaire.getQuestionTitle()); //创建问题节点
                    userrepository.questionNodeToCore(questionNodeId,questionnairecore.getId()); //将这个问题节点连接至问卷中心节点
            int qnum=0,qsum=questionnaire.getQuestionContent().size();
            while(qnum!=qsum - 1)
            {
                switch(qnum) //问题选项作为节点属性，补充到问卷问题节点
                {
                    case 0: userrepository.addNodeAttributesQ1(questionNodeId,questionnaire.getQuestionContent().get(0));
                    case 1: userrepository.addNodeAttributesQ2(questionNodeId,questionnaire.getQuestionContent().get(1));
                    case 2: userrepository.addNodeAttributesQ3(questionNodeId,questionnaire.getQuestionContent().get(2));
                    case 3: userrepository.addNodeAttributesQ4(questionNodeId,questionnaire.getQuestionContent().get(3));
                    case 4: userrepository.addNodeAttributesQ5(questionNodeId,questionnaire.getQuestionContent().get(4));
                    case 5: userrepository.addNodeAttributesQ6(questionNodeId,questionnaire.getQuestionContent().get(5));
                    case 6: userrepository.addNodeAttributesQ7(questionNodeId,questionnaire.getQuestionContent().get(6));
                    case 7: userrepository.addNodeAttributesQ8(questionNodeId,questionnaire.getQuestionContent().get(7));
                }
                qnum = qnum + 1;
            }
        }
        return null;
    }

}
