package com.se.se_part.Dao;

import com.se.se_part.Entity.Group;
import com.se.se_part.Entity.User;
import org.springframework.data.neo4j.repository.query.Query;

public interface GroupRepository {
    @Query("match(n:Group) where n.groupName = $gName return n")
    Group FindByGroupName(String gName);

    @Query("create(group:Group{}) return group")
    Group CreateNewGroup();
}
