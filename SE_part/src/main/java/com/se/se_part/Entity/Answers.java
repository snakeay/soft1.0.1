package com.se.se_part.Entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Property;

import java.util.List;

@Data
public class Answers
{
    @Property
    private List<String> answer;
}
