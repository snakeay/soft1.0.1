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
    @Query("create(questionnairecore:QuestionnaireCore{title:$title}) return questionnairecore")
    QuestionnaireCore createQuestionnaireCore(String title);

    //TODO:将问卷中心节点连接至用户组
    @Query("match(a:QuestionCore),(b:Group) where id(a)=$coreId and id(b)=$groupId create (a)-[r:questionBelongsTo]->(b)")
    void questionCoreToGroup(Long coreId, Long groupId);

    //TODO：创建问卷题目节点
    @Query("create(n:QuestionNode{Q1:\"\",Q2:\"\",Q3:\"\",Q4:\"\",Q5:\"\",Q6:\"\",Q7:\"\",Q8:\"\",title:$questiontitle,type:$questiontype}) return id(n)")
    Long createQuestionNode(Integer questiontype, String questiontitle);

    //TODO：将问题节点连接至问卷中心节点
    @Query("match(a:QuestionNode),(b:QuestionCore) where id(a)=$nodeId and id(b)=$coreId create (a)-[r:questionBelongsTo]->(b)")
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


}
