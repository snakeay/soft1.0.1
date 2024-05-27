import com.se.se_part.Controller.UserController;
import com.se.se_part.Dao.UserRepository;
import com.se.se_part.Entity.User;
import com.se.se_part.Main;
import com.se.se_part.Utils.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;
@SpringBootTest(classes = Main.class)
public class Neo4jSpringBootApplicationTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserController userController;
    @Test
    public void TestCreate()
    {
        //TODO:测试login函数
        User user=new User();
        Result result = new Result();
        //Optional<User> byId = Optional.ofNullable(userRepository.findByNodeId("u001"));
        user.setAccount("user100111");
        user.setPassword("user1123");
        result = userController.login(user);
        System.out.println(result.getCode());

        //TODO:测试id(n)
        //System.out.println(userRepository.FindByUserId());

        //TODO:测试注册函数

    }
    @Test
    public void Taetregist()
    {
        User user =new User();
        Result result= new Result();
        user.setAccount("user100111");
        user.setPassword("user1123");
        user.setNickname("井盖搬运工");
        result = userController.register(user);
        System.out.println(result.getCode());
    }

    @Test
    public void TestGroup()
    {

    }
}
