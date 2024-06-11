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
public class Questionnaire
{
    @Property
    private Integer type; //type 012 -> 单选、多选、提问
    @Property
    private String questionTitle; //题头

    private String answer; //回显用的答案
    @Property
    private List<String> questionContent; //选择题的选项

}
