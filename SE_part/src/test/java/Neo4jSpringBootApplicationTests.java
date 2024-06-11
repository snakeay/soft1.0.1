import com.se.se_part.Controller.AdminController;
import com.se.se_part.Controller.GroupController;
import com.se.se_part.Controller.UserController;
import com.se.se_part.Dao.AnswerCoreRepository;
import com.se.se_part.Dao.GroupRepository;
import com.se.se_part.Dao.UserRepository;
import com.se.se_part.Entity.*;
import com.se.se_part.Main;
import com.se.se_part.Service.UserService;
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
    private UserService userService;
    @Autowired
    private UserController userController;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupController groupController;
    @Autowired
    private AdminController adminController;
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
        List<Long> list4 = Arrays.asList(3L);
        String token = jwtHelper.createToken(2L);
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


        /*
        //TODO:测试填写表单函数
        String token = jwtHelper.createToken(0L);
        Long questionFormId = 8L;
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

         */


        /*
        //TODO:测试找到用户所有相关的问卷中心节点的标题和id
        String token = jwtHelper.createToken(0L);
        Result result = userController.getAllFormsIdAndTitle(token);
        System.out.println(result.getData());
         */


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



/*
        //TODO：测试加入一个用户组
        String token = jwtHelper.createToken(0L);
        Group group =new Group();
        group.setId(2L);
        Result result = groupController.joinGroup(token,group);
        System.out.println(result.getCode());

 */




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

    @Test
    public void testPostQuestionnaire()
    {
        String token = jwtHelper.createToken(2L);
        Result result = userController.getFormDetails(token,25L);
        System.out.println(result.getData());
    }

    @Test
    public void testGetAnswer()
    {
        String token = jwtHelper.createToken(0L);
        Long questionFormId = 8L;
        AnswerCoreRepository answercorerepository;
        //AnswerCore answerCore = answercorerepository.getAnswerCore(0L, questionFormId);
        Result result = userController.getAnswerFormDetails(token,questionFormId);
        System.out.println(result.getData());
        //System.out.println(answerCore);
    }

    @Test
    public void testCreateQuestionnaire()
    {
        //TODO:测试创建表单函数
        List<Questionnaire> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        List<Long> list4 = Arrays.asList(35L);
        String token = jwtHelper.createToken(37L);
        Questionnaire Q1=new Questionnaire();
        Questionnaire Q2=new Questionnaire();
        Questionnaire Q3=new Questionnaire();
        Q1.setType(0);
        Q2.setType(1);
        Q3.setType(2);
        Q1.setQuestionTitle("姓名：");
        Q2.setQuestionTitle("年龄：");
        Q3.setQuestionTitle("您的家庭住址是：");
//        list2.add("选项A的内容");
//        list2.add("选项B的内容");
//        list2.add("选项C的内容");
//        list2.add("选项D的内容");
//        list3.add("多选题选项A的内容");
//        list3.add("多选题选项B的内容");
//        list3.add("多选题选项C的内容");
//        list3.add("多选题选项D的内容");
//        list3.add("多选题选项E的内容");
//        list3.add("多选题选项F的内容");
//        list3.add("多选题选项G的内容");
//        Q1.setQuestionContent(list2);
//        Q2.setQuestionContent(list3);
        Q1.setQuestionContent(null);
        Q2.setQuestionContent(null);
        Q3.setQuestionContent(null);
        list1.add(Q1);
        list1.add(Q2);
        list1.add(Q3);
        Result result = userController.createNewForm(list1,"第七次表单测试",token,list4);
        System.out.println(result.getCode());
    }

    @Test
    public void testFinishQuestionnaire()
    {
        //TODO:测试填写表单函数
        String token = jwtHelper.createToken(36L);
        Long questionFormId = 40L;
        List<Answers> answers = new ArrayList<>();
        Answers answers1 = new Answers();
        Answers answers2 = new Answers();
        Answers answers3 = new Answers();
        List<String> list1 = Arrays.asList("田文镜");
        List<String> list2 = Arrays.asList("60");
        List<String> list3 = Arrays.asList("京师");
        answers1.setAnswer(list1);
        answers2.setAnswer(list2);
        answers3.setAnswer(list3);
        answers.add(answers1);
        answers.add(answers2);
        answers.add(answers3);
        Result result = userController.submitAnswer(answers,questionFormId,token);
    }

    @Test
    public void testGetAllAnswerFormIdAndFiller()
    {
        String token = jwtHelper.createToken(10L);
        Result result = userController.getAllAnswerFormIdAndFiller(8L);
        //System.out.println(result.getData());
    }

    @Test
    public void testsearchInfoByOneType()
    {
        Result result = userController.searchInfoByOneType(8L,"多项选择题：");
        System.out.println(result.getData());
    }

    @Test
    public void testgetCreatedFormTitleAndId()
    {
        String token = jwtHelper.createToken(10L);
        Result result = userController.getCreatedFormTitleAndId(token);
        System.out.println(result.getData());
    }

    @Test
    public void testgetFinishedFormTitleAndId()
    {
        String token = jwtHelper.createToken(2L);
        Result result = userController.getFinishedFormTitleAndId(token);
    }

    @Test
    public void testgetNotFinishedFormTitleAndId()
    {
        String token = jwtHelper.createToken(10L);
        Result result = userController.getNotFinishedFormTitleAndId(token);
    }

    @Test
    public void testgetAllUsers()
    {
        Result result = adminController.getAllUsers();
        System.out.println(result.getData());
    }

    @Test
    public void testdeleteUsers()
    {
        Long targetId = 36L;
        adminController.deleteUsers(targetId);
    }

    @Test
    public void testgetAllGroups()
    {
        Result result = adminController.getAllGroups();
        System.out.println(result.getData());
    }

    @Test
    public void testcheckGroup()
    {
        Long groupId = 34L;
        Result result = adminController.checkGroup(groupId);
        System.out.println(result.getData());
    }

    @Test
    public void testdeleteUserFormGroup()
    {
        Long userId = 36L;
        Long groupId =35L;
        adminController.deleteUserFormGroup(userId,groupId);
    }

    @Test
    public void testjoinUserIntoGroup()
    {
        Long userId = 38L;
        Long groupId = 35L;
        adminController.joinUserIntoGroup(userId,groupId);
    }

    @Test
    public void testdeleteGroup()
    {
        Long groupId = 35L;
        adminController.deleteGroup(groupId);
    }

    @Test
    public void testgetAllquestionnaires()
    {
        Result result = adminController.getAllQuestionnaires();
        System.out.println(result.getData());
    }

    @Test
    public void testcreateGroup()
    {
        String groupName = "管理员测试组2";
        Result result = adminController.createGroup(groupName);
    }

    @Test
    public void testgetQuestionnaireDetails()
    {
        String adminToken = jwtHelper.createToken(36L);
        Long questionnaireCoreId = 40L;
        Result result = adminController.getQuestionnaireDetails(adminToken, questionnaireCoreId);
        System.out.println(result.getData());
    }

    @Test
    public void testgetAllAnswerForm()
    {
        Long questionnaireCoreId = 25L;
        Result result = adminController.getAllAnswerForm(questionnaireCoreId);
        System.out.println(result.getData());
    }

    @Test
    public void testgetAnswerFormDetails()
    {
        Long answerCoreId = 13L;
        Result result = adminController.getAnswerFormDetails(answerCoreId);
        System.out.println(result.getData());
    }

    @Test
    public void testModeling()
    {
        userService.dataModeling(40L,44L);
    }
}
