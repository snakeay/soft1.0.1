import com.se.se_part.Controller.GroupController;
import com.se.se_part.Controller.UserController;
import com.se.se_part.Dao.GroupRepository;
import com.se.se_part.Dao.UserRepository;
import com.se.se_part.Entity.*;
import com.se.se_part.Main;
import com.se.se_part.Utils.JwtHelper;
import com.se.se_part.Utils.Result;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@SpringBootTest(classes = Main.class)
public class Neo4jSpringBootApplicationTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserController userController;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupController groupController;
    @Autowired
    private JwtHelper jwtHelper;

    @Test
    public void Testregist()
    {
        User user =new User();
        Result result= new Result();
        user.setAccount("user100111");
        user.setPassword("user1123");
        user.setNickname("张三");
        result = userController.register(user);
        System.out.println(result.getCode());
    }

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
        //TODO:测试创建表单函数
        List<Questionnaire> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        List<Long> list4 = Arrays.asList(2L);
        String token = jwtHelper.createToken(1L);
        Questionnaire Q1=new Questionnaire();
        Questionnaire Q2=new Questionnaire();
        Questionnaire Q3=new Questionnaire();
        Q1.setType(0);
        Q2.setType(1);
        Q3.setType(2);
        Q1.setQuestionTitle("单项选择题：");
        Q2.setQuestionTitle("多项选择题：");
        Q3.setQuestionTitle("填空题：");
        list2.add("选项A的内容");
        list2.add("选项B的内容");
        list2.add("选项C的内容");
        list2.add("选项D的内容");
        list3.add("多选题选项A的内容");
        list3.add("多选题选项B的内容");
        list3.add("多选题选项C的内容");
        list3.add("多选题选项D的内容");
        list3.add("多选题选项E的内容");
        list3.add("多选题选项F的内容");
        list3.add("多选题选项F的内容");
        Q1.setQuestionContent(list2);
        Q2.setQuestionContent(list3);
        Q3.setQuestionContent(null);
        list1.add(Q1);
        list1.add(Q2);
        list1.add(Q3);
        Result result = userController.createNewForm(list1,"第二次表单测试",token,list4);
        System.out.println(result.getCode());

         */



        //TODO:测试填写表单函数
        String token = jwtHelper.createToken(0L);
        Long questionFormId = 3L;
        List<Answers> answers = new ArrayList<>();
        Answers answers1 = new Answers();
        Answers answers2 = new Answers();
        Answers answers3 = new Answers();
        List<String> list1 = Arrays.asList("选项a");
        List<String> list2 = Arrays.asList("选项a","选项b");
        List<String> list3 = Arrays.asList("填空题");
        answers1.setAnswer(list1);
        answers2.setAnswer(list2);
        answers3.setAnswer(list3);
        answers.add(answers1);
        answers.add(answers2);
        answers.add(answers3);
        Result result = userController.submitAnswer(answers,questionFormId,token);

    }

    @Test
    public void TestGroup()
    {

        /*
        //TODO:测试建立一个用户组
        String token = jwtHelper.createToken(1L);
        Group group =new Group();
        group.setGroupName("学生信息收集组");
        Result result = groupController.createGroup(token,group);
        System.out.println(result.getData());
        System.out.println(result.getCode());
        */




        //TODO：测试加入一个用户组
        String token = jwtHelper.createToken(0L);
        Group group =new Group();
        group.setId(2L);
        Result result = groupController.joinGroup(token,group);
        System.out.println(result.getCode());




/*
        //TODO：测试退出一个用户组
        String token = jwtHelper.createToken(0L);
        Group group =new Group();
        group.setId(2L);
        Result result = groupController.exitGroup(token,group);
        System.out.println(result.getCode());

 */



        /*
        //TODO：测试组创建者删除组内一个人
        Group group=new Group();
        User user=new User();
        group.setId(8L);
        user.setId(2L);
        Result result = groupController.administrateGroup(group,user);
        System.out.println(result.getCode());
         */
    }

    @Test
    public void tokenToId()
    {
        String token = "";
        System.out.println("token =" + jwtHelper.getUserId(token));
    }

}
