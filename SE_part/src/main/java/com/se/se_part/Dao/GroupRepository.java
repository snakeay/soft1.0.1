package com.se.se_part.Dao;

import com.se.se_part.Entity.Group;
import com.se.se_part.Entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface GroupRepository extends Neo4jRepository<Group,Long> {
    @Query("match(n:Group) where id(n) = $groupId return n")
    Group FindByGroupId(Long groupId);

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

    /*管理员所需组功能函数*/
    @Query("match(n:Group) return n")
    List<Group> adminGetAllGroups();

    //管理员通过组id删除用户组
    @Query("match(n:Group) where id(n)=$groupId detach delete n")
    void deleteGroup(Long groupId);
}
