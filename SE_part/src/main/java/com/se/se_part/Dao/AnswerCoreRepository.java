package com.se.se_part.Dao;

import com.se.se_part.Entity.AnswerCore;
import com.se.se_part.Entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface AnswerCoreRepository extends Neo4jRepository<AnswerCore,Long>
{
    //通过用户id和问题卷中心节点id找到用户自己填写的答案卷中心节点
    @Query("MATCH (a)-[r:finishForm]->(answercore)-[rr:answersToquestion]->(b) where id(a)=$userId and id(b)=$questionnaireCoreId return id(answercore)")
    Long getAnswerCore(Long userId, Long questionnaireCoreId);

    //通过问题卷中心节点id找到用户创建的某个指定问卷的所有答案卷中心节点
    @Query("match(a)-[r:answersToquestion]->(b) where id(b)=$questionnaireCoreId return STARTNODE(r)")
    List<AnswerCore> getAllAnswerCoreByQueCoreId(Long questionnaireCoreId);
}
