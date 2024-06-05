package com.se.se_part.Entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.util.List;

@Data
@Node("AnswerNode")
public class AnswerNode
{
    @Id
    @GeneratedValue
    private Long id;
    @Property
    private int number;
    @Property
    private String Q1;
    @Property
    private String Q2;
    @Property
    private String Q3;
    @Property
    private String Q4;
    @Property
    private String Q5;
    @Property
    private String Q6;
    @Property
    private String Q7;
    @Property
    private String Q8;

}
