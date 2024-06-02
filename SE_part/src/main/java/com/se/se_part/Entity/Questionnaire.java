package com.se.se_part.Entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.util.List;

/**
 * @author DWC
 */
@Data
public class Questionnaire //数据库中的问题节点
{
    @Id
    @GeneratedValue
    private Long id;
    @Property
    private Integer type; //type 012 -> 单选、多选、提问
    @Property
    private String questionTitle; //题头
    @Property
    private List<String> questionContent; //选择题的选项
}
