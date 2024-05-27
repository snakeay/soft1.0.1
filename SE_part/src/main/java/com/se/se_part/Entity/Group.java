package com.se.se_part.Entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;

import java.util.List;

@Data
public class Group {
    @Id
    @GeneratedValue
    private Integer id;
    @Property
    private String groupName;
}
