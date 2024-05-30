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
    private List<String> answers;
}
