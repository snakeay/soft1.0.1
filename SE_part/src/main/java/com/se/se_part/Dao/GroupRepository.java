package com.se.se_part.Dao;

import com.se.se_part.Entity.Group;
import com.se.se_part.Entity.QuestionnaireCore;
import com.se.se_part.Entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface GroupRepository extends Neo4jRepository<Group,Long> {
    @Query("match(n:Group) where n.groupName = $gName return n")
    Group FindByGroupName(String gName);

    @Query("create (group:Group{groupName:$groupName}) return group")
    Group CreateNewGroup(String groupName);

    @Query("match (user:User),(group:Group) where id(user)=$userId and id(group)=$groupId create (user)-[r:userBelongsTo]->(group)")
    void JoinGroup(Long userId,Long groupId);

    @Query("match (user:User)-[rel:userBelongsTo]->(group:Group) where id(user) = $userId and id(group) = $groupId delete rel")
    void ExitGroup(Long userId, Long groupId);

    @Query("match (user:User),(group:Group) where id(user)=$userId and id(group)=$groupId create (user)-[r:userAdministrate]->(group)")
    void ConnectUserToGroup(Long userId, Long groupId);

    @Query("match (user:User)-[rel:userBelongsTo]->(group:Group) where id(user) = $userId and id(group) = $groupId delete rel")
    void deleteGroupUser(Long userId, Long groupId);

    //通过用户id找到用户所属用户组
    @Query("match (a)-[r:userBelongsTo]->(b) where id(a)=$userId return ENDNODE(r)")
    List<Group> findBelongGroupByUserId(Long userId);

    //通过用户id找到用户所创建的组
    @Query("match (a)-[r:userAdministrate]->(b) where id(a)=$userId return ENDNODE(r)")
    List<Group> findAdministrateGroupByUserId(Long userId);


}
