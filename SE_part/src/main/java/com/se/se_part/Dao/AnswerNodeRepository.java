package com.se.se_part.Dao;

import com.se.se_part.Entity.AnswerCore;
import com.se.se_part.Entity.AnswerNode;
import com.se.se_part.Entity.Answers;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface AnswerNodeRepository extends Neo4jRepository<AnswerNode,Long>
{
    //利用答案中心节点id找到所有答案节点
    @Query("match (a)-[r:answerBelongsTo]->(b) where id(b)=$coreId return a")
    List<AnswerNode> getAllAnswerNodesByCoreId(Long coreId);

    //利用答案中心节点id和题序number找到一个答案节点
    @Query("match (a)-[r:answerBelongsTo]->(b) where id(b)=$coreId and a.number=$number return a")
    AnswerNode getAllAnswerNodesByCoreIdAndNumber(Long coreId,int number);

    //找到指定number的答案节点
    @Query("match(a)-[r:answerBelongsTo]->(b) where id(b)=$AnswerCoreId and a.number = $number return a")
    AnswerNode getAnswerNodeByNumber(Long AnswerCoreId, int number);
}
