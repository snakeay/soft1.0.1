package com.se.se_part.Dao;

import com.se.se_part.Entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */
@Repository
@Component
public interface UserRepository extends Neo4jRepository<User,Long>
{
    //根据id寻找用户节点
    @Query("match(n:User) where n.uid = $nodeId return n")
    User findByNodeId(String nodeId);

    //登录时所需的查找用户账号函数，返回User类
    @Query("match(n:User) where n.account = $u_account return n")
    User FindByUserAccount(String u_account);

    //登录时所需的查找用户密码，返回String类
    @Query("match(n:User) where n.account = $user.account return n.password")
    String GetUserPassword(User user);

    //测试id(n)函数的语句
    @Query("match(n:User) where n.account=\"user1\" return id(n)")
    Long FindByUserId();

    //创建一个新用户的CQL函数
    @Query("create(user:User{account:$new_user_account,password:$new_user_password,nickname:$new_user_nickname}) return user")
    User CreateNewUser(String new_user_account, String new_user_password, String new_user_nickname);
}
