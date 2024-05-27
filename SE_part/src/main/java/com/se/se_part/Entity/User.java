package com.se.se_part.Entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.io.Serializable;

/*
@author: DWC
 */
@Data
@Node("User")
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Property
    private String account; //账号（唯一的）
    @Property
    private String password; //密码
    @Property
    private String nickname; //姓名
}
