import com.se.se_part.Controller.UserController;
import com.se.se_part.Dao.GroupRepository;
import com.se.se_part.Dao.UserRepository;
import com.se.se_part.Entity.Group;
import com.se.se_part.Entity.QuestionNode;
import com.se.se_part.Entity.Questionnaire;
import com.se.se_part.Entity.User;
import com.se.se_part.Main;
import com.se.se_part.Utils.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;
@SpringBootTest(classes = Main.class)
public class Neo4jSpringBootApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void TestCreate()
    {
        /*
        //TODO:测试login函数
        User user=new User();
        Result result = new Result();
        //Optional<User> byId = Optional.ofNullable(userRepository.findByNodeId("u001"));
        user.setAccount("user100111");
        user.setPassword("user1123");
        result = userController.login(user);
        System.out.println(result.getCode());
        */

        //TODO:测试id(n)
        //System.out.println(userRepository.FindByUserId());

        /*
        //TODO:测试通过用户id找到所属用户组
        List<Group> groups = groupRepository.findBelongGroupByUserId(2L);
        System.out.println(groups);
        */

        /*
        //TODO:测试通过用户id找到所管理的组
        List<Group> group = groupRepository.findAdministrateGroupByUserId(10L);
        System.out.println(group);
        */

        /*
        //TODO:测试创建问题节点函数
        Long questionNode = userRepository.createQuestionNode(1,"问题1");
        System.out.println(questionNode);
        Questionnaire newNode = userRepository.addNodeAttributes(6L,"Q3","问题3");
        System.out.println(newNode);
         */


    }

    /*
    @Test
    public void Taetregist()
    {
        UserController usercontroller = new UserController();
        Result result = usercontroller.getgroupInfo(2L);
        System.out.println(result.getData());
    }
     */

}
