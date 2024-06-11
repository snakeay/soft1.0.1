package com.se.se_part.Dao;

import com.se.se_part.Entity.QuestionNode;
import com.se.se_part.Entity.QuestionnaireCore;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface QuestionNodeRepository extends Neo4jRepository<QuestionNode,Long>
{
    //通过问卷中心节点id找到整个问卷的所有题目
    @Query("match(a)-[r:questionBelongsTo]->(b) where id(b)=$coreId return a")
    List<QuestionNode> getQuestionNodeByCoreId(Long coreId);

    //通过title找到number
    @Query("match(a)-[r:questionBelongsTo]->(b) where id(b)=$coreId and a.title = $title return a.number")
    int getNumberByTitle(Long coreId, String title);

}