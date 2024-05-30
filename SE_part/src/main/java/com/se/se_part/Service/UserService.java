package com.se.se_part.Service;

import com.se.se_part.Entity.Answers;
import com.se.se_part.Entity.Questionnaire;
import com.se.se_part.Entity.User;
import com.se.se_part.Utils.Result;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService  {
    /*TODO:登录功能
     *@author DWC
     *date:2024/5/23
     */
    Result login(User user);

    /*TODO:注册功能
    * @author DWC
    * date:2024/5/26
    * */
    Result register(User user);

    /*TODO:创建表单
    * @author DWC
    * date:2024/5/28
    * */
    Result createNewForm(List<Questionnaire> questionnaires, String questionnaireTitlem, String token, List<Long> targetGroupIds);

    /*TODO:通过token找到用户id，再通过用户id找到用户所属于的组
    * @author DWC
    * date:2024/5/29*/
    Result getGroupBelongstoInfo(String token);/*String token*/

    /*TODO:通过token找到用户id，再通过用户id找到用户所管理的组
     * @author DWC
     * date:2024/5/29*/
    Result createFormFindGroupAdministratedTo(String token);

    /*TODO：创建答案卷
     * @author DWC
     * date:2024/5/29*/
    Result createAnswerForm(List<Answers> answerList, Long questionnaireCoreId);


}
