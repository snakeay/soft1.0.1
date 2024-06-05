package com.se.se_part.Dao;

import com.se.se_part.Entity.QuestionnaireCore;
import com.se.se_part.Entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface QuestionnaireCoreRepository extends Neo4jRepository<QuestionnaireCore,Long>
{
    //通过组id找到组内所有的问卷中心节点
    @Query("match(a)-[r:questionFormBelongsTo]->(b) where id(b)=$groupId return STARTNODE(r)")
    List<QuestionnaireCore> findQuestionnaireCore(Long groupId);

    //通过用户id将问题卷创建者和问题卷中心节点连接在一起
    @Query("match(a:User),(b:QuestionnaireCore) where id(a)=$creatorId and id(b)=$questionnaireCore create (a)-[r:userCreateForm]->(b)")
    void creatorToQuestionnaireCore(Long creatorId, Long questionnaireCore);

    //通过用户id找到其创建的所有问题卷中心节点
    @Query("match(a)-[r:userCreateForm]->(b) where id(a)=$userId return b ")
    List<QuestionnaireCore> getAllCreatedQuestionnaireCore(Long userId);

}