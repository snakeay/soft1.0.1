package com.se.se_part.Entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Data
@Node("AnswerCore")
public class AnswerCore
{
    @Id
    @GeneratedValue
    private Long id;
    @Property
    private Long questionnaireCoreId;
}
