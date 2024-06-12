package com.se.se_part.Dao;

import com.se.se_part.Entity.Administrator;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface AdministratorRepository
{
    //通过account找到admin
    @Query("MATCH(n:Administrator) WHERE n.account=$account RETURN n")
    Administrator FindByAdminAccount(String account);
}
