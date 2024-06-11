package com.se.se_part.Dao;

import com.se.se_part.Entity.*;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DWC
 */
@Repository
@Component
public interface UserRepository extends Neo4jRepository<User,Long>
{
    //TODO：根据id寻找用户节点
    @Query("match(n:User) where n.uid = $nodeId return n")
    User findByNodeId(String nodeId);

    //TODO：登录时所需的查找用户账号函数，返回User类
    @Query("match(n:User) where n.account = $u_account return n")
    User FindByUserAccount(String u_account);

    //TODO：登录时所需的查找用户密码，返回String类
    @Query("match(n:User) where n.account = $user.account return n.password")
    String GetUserPassword(User user);

    //TODO：测试id(n)函数的语句
    @Query("match(n:User) where n.account=\"user1\" return id(n)")
    Long FindByUserId();

    //TODO：创建一个新用户的CQL函数
    @Query("create(user:User{account:$new_user_account,password:$new_user_password,nickname:$new_user_nickname}) return user")
    User CreateNewUser(String new_user_account, String new_user_password, String new_user_nickname);

    //TODO：创建问卷中心节点
    @Query("create(questionnairecore:QuestionnaireCore{title:$title}) return id(questionnairecore)")
    Long createQuestionnaireCore(String title);

    //TODO:将问卷中心节点连接至用户组
    @Query("match(a:QuestionnaireCore),(b:Group) where id(a)=$coreId and id(b)=$groupId create (a)-[r:questionFormBelongsTo]->(b)")
    void questionCoreToGroup(Long coreId, Long groupId);

    //TODO：创建问卷题目节点
    @Query("create(n:QuestionNode{number:$number, Q1:\"\",Q2:\"\",Q3:\"\",Q4:\"\",Q5:\"\",Q6:\"\",Q7:\"\",Q8:\"\",title:$questiontitle,type:$questiontype}) return id(n)")
    Long createQuestionNode(int number, Integer questiontype, String questiontitle);

    //TODO：将问题节点连接至问卷中心节点
    @Query("match(a:QuestionNode),(b:QuestionnaireCore) where id(a)=$nodeId and id(b)=$coreId create (a)-[r:questionBelongsTo]->(b)")
    void questionNodeToCore(Long nodeId,Long coreId);


/*---------------------------------------------------------------------以下函数是为了给问题节点填充属性-----------------------------------------------------------------------------*/
    //TODO：问题选项作为节点属性，补充到问卷问题节点
    @Query("match(n:QuestionNode) where id(n)=$questionNodeId set n.Q1 = $questioncontent")
    void addNodeAttributesQ1(Long questionNodeId, String questioncontent);
    @Query("match(n:QuestionNode) where id(n)=$questionNodeId set n.Q2 = $questioncontent")
    void addNodeAttributesQ2(Long questionNodeId, String questioncontent);
    @Query("match(n:QuestionNode) where id(n)=$questionNodeId set n.Q3 = $questioncontent")
    void addNodeAttributesQ3(Long questionNodeId, String questioncontent);
    @Query("match(n:QuestionNode) where id(n)=$questionNodeId set n.Q4 = $questioncontent")
    void addNodeAttributesQ4(Long questionNodeId, String questioncontent);
    @Query("match(n:QuestionNode) where id(n)=$questionNodeId set n.Q5 = $questioncontent")
    void addNodeAttributesQ5(Long questionNodeId, String questioncontent);
    @Query("match(n:QuestionNode) where id(n)=$questionNodeId set n.Q6 = $questioncontent")
    void addNodeAttributesQ6(Long questionNodeId, String questioncontent);
    @Query("match(n:QuestionNode) where id(n)=$questionNodeId set n.Q7 = $questioncontent")
    void addNodeAttributesQ7(Long questionNodeId, String questioncontent);
    @Query("match(n:QuestionNode) where id(n)=$questionNodeId set n.Q8 = $questioncontent")
    void addNodeAttributesQ8(Long questionNodeId, String questioncontent);
    /*----------------------------------------------------------------------------------------------------------------------------------------------------------------*/

    //TODO:利用id找到问卷中心节点
    @Query("match(n:QuestionnaireCore) where id(n)=$questionnaireCoreId return n.title")
    String getquestionnaireCore(Long questionnaireCoreId);

    //TODO：创建问卷中心节点
    @Query("create(answercore:AnswerCore{title:$title}) return id(answercore)")
    Long createAnswerCore(String title);

    //TODO:将用户和答案中心节点相连
    @Query("match(a:User),(b:AnswerCore) where id(a)=$userId and id(b)=$answerCoreId create (a)-[r:finishForm]->(b)")
    void usertoAnswerCore(Long userId,Long answerCoreId);

    //TODO:将答案中心节点连接至问题卷中心节点
    @Query("match(a:AnswerCore),(b:QuestionnaireCore) where id(a)=$answerCoreId and id(b)=$questionnaireCoreId create (a)-[r:answersToquestion]->(b)")
    void answerCoreToQuestionnaireCoreId(Long answerCoreId, Long questionnaireCoreId);

    //TODO:创建答案节点
    @Query("create(n:AnswerNode{number:$number, Q1:\"\",Q2:\"\",Q3:\"\",Q4:\"\",Q5:\"\",Q6:\"\",Q7:\"\",Q8:\"\"}) return id(n)")
    Long createAnswerNode(int number);

    //TODO：将问题节点连接至问卷中心节点
    @Query("match(a:AnswerNode),(b:AnswerCore) where id(a)=$nodeId and id(b)=$coreId create (a)-[r:answerBelongsTo]->(b)")
    void answerNodeToCore(Long nodeId,Long coreId);


    /*---------------------------------------------------------------------以下函数是为了给问题节点填充属性-----------------------------------------------------------------------------*/
    //TODO：答案作为节点属性，补充到问卷问题节点
    @Query("match(n:AnswerNode) where id(n)=$answerNodeId set n.Q1 = $questioncontent")
    void addNodeAttributesA1(Long answerNodeId, String questioncontent);
    @Query("match(n:AnswerNode) where id(n)=$answerNodeId set n.Q2 = $questioncontent")
    void addNodeAttributesA2(Long answerNodeId, String questioncontent);
    @Query("match(n:AnswerNode) where id(n)=$answerNodeId set n.Q3 = $questioncontent")
    void addNodeAttributesA3(Long answerNodeId, String questioncontent);
    @Query("match(n:AnswerNode) where id(n)=$answerNodeId set n.Q4 = $questioncontent")
    void addNodeAttributesA4(Long answerNodeId, String questioncontent);
    @Query("match(n:AnswerNode) where id(n)=$answerNodeId set n.Q5 = $questioncontent")
    void addNodeAttributesA5(Long answerNodeId, String questioncontent);
    @Query("match(n:AnswerNode) where id(n)=$answerNodeId set n.Q6 = $questioncontent")
    void addNodeAttributesA6(Long answerNodeId, String questioncontent);
    @Query("match(n:AnswerNode) where id(n)=$answerNodeId set n.Q7 = $questioncontent")
    void addNodeAttributesA7(Long answerNodeId, String questioncontent);
    @Query("match(n:AnswerNode) where id(n)=$answerNodeId set n.Q8 = $questioncontent")
    void addNodeAttributesA8(Long answerNodeId, String questioncontent);
    /*----------------------------------------------------------------------------------------------------------------------------------------------------------------*/

    //通过答案卷中心节点id找到用户（填写者）
    @Query("match(a)-[r:finishForm]->(b) where id(b)=$answerCoreId return a.nickname")
    String getFillerNickname(Long answerCoreId);

    //通过答案卷中心节点id找到用户（填写者）返回用户id
    @Query("match(a)-[r:finishForm]->(b) where id(b)=$answerCoreId return id(a)")
    Long getUserIdByAnswerCoreId(Long answerCoreId);

    //TODO：按照用户Id查找用户
    @Query("match(n:User) where id(n)=$userId return n")
    User FindByUserId(Long userId);


    /*-----------------------------------------TODO:以下是管理员的相关函数-------------------------------------------------------------------------------------------------------*/
    //TODO:管理员找到所有用户
    @Query("match(n:User) return n")
    List<User> adminGetAllUsers();

    //TODO:管理员通过id删除用户
    @Query("match(n:User) where id(n)=$userId detach delete n")
    void adminDelteUserById(Long userId);

    //TODO:通过组id找到组里所属的用户
    @Query("MATCH(a)-[r:userBelongsTo]->(b) WHERE id(b)=$groupId RETURN a")
    List<User> getUsersInGroup(Long groupId);

    //TODO：通过组id找到组创建者
    @Query("match(a)-[r:userAdministrate]->(b) where id(b)=$groupId return a")
    User getadminInGroup(Long groupId);

    /*-----------------------------------------TODO:组管理所用-------------------------------------------------------------------------------------------------------*/
    @Query("match (a)-[rel:userBelongsTo]->(b) where id(b) = $groupId return startnode(rel)")
    List<User> findMembersJoinGroup(Long groupId);

    @Query("match (a)-[rel:userAdministrate]->(b) where id(b) = $groupId return startnode(rel)")
    User findUserCreatedByGroupId(Long groupId);

    /*------------------------------------------TODO：数据建模---------------------------------------------------------------------------------------------------------*/
    //通过用户id找到姓名节点，获取姓名
    @Query("match(a:UserName)-[r:userAttribute]->(b) where id(b)=$userId return a.Name")
    String getUserName(Long userId);
    //更新姓名节点属性
    @Query("MATCH(a:UserName)-[r:userAttribute]->(b) WHERE id(b)=$userId SET a.Name=$userName")
    void updateUserName(Long userId,String userName);
    //给用户建立姓名节点
    @Query("CREATE(n:UserName{Name:$name}) return id(n)")
    Long createUserName(String name);
    //将姓名节点连接到用户上
    @Query("MATCH(a),(b) WHERE id(a)=$attributeId AND id(b)=$userId CREATE(a)-[r:userAttribute]->(b)")
    void attributeNameToUser(Long attributeId,Long userId);

    //通过用户id找到年龄节点，获取年龄
    @Query("match(a:UserAge)-[r:userAttribute]->(b) where id(b)=$userId return a.Age")
    String getUserAge(Long userId);
    //更新年龄节点属性
    @Query("MATCH(a:UserAge)-[r:userAttribute]->(b) WHERE id(b)=$userId SET a.Age=$userAge")
    void updateUserAge(Long userId,String userAge);
    //给用户建立年龄节点
    @Query("CREATE(n:UserAge{Age:$age}) return id(n)")
    Long createUserAge(String age);
    //将年龄节点连接到用户上
    @Query("MATCH(a),(b) WHERE id(a)=$attributeId AND id(b)=$userId CREATE(a)-[r:userAttribute]->(b)")
    void attributeAgeToUser(Long attributeId,Long userId);

    //通过用户id找到性别节点，获取性别
    @Query("match(a:UserSex)-[r:userAttribute]->(b) where id(b)=$userId return a.Sex")
    String getUserSex(Long userId);
    //更新性别节点属性
    @Query("MATCH(a:UserSex)-[r:userAttribute]->(b) WHERE id(b)=$userId SET a.Sex=$userSex")
    void updateUserSex(Long userId,String userSex);
    //给用户建立性别节点
    @Query("CREATE(n:UserSex{Sex:$sex}) return id(n)")
    Long createUserSex(String sex);
    //将性别节点连接到用户上
    @Query("MATCH(a),(b) WHERE id(a)=$attributeId AND id(b)=$userId CREATE(a)-[r:userAttribute]->(b)")
    void attributeSexToUser(Long attributeId,Long userId);

    //通过用户id找到联系方式节点，获取联系方式
    @Query("match(a:UserTel)-[r:userAttribute]->(b) where id(b)=$userId return a.Tel")
    String getUserTel(Long userId);
    //更新联系方式节点属性
    @Query("MATCH(a:UserTel)-[r:userAttribute]->(b) WHERE id(b)=$userId SET a.Tel=$userTel")
    void updateUserTel(Long userId,String userTel);
    //给用户建立联系方式节点
    @Query("CREATE(n:UserTel{Tel:$tel}) return id(n)")
    Long createUserTel(String tel);
    //将联系方式节点连接到用户上
    @Query("MATCH(a),(b) WHERE id(a)=$attributeId AND id(b)=$userId CREATE(a)-[r:userAttribute]->(b)")
    void attributeTelToUser(Long attributeId,Long userId);
}
