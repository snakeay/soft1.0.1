package com.se.se_part.Controller;

import com.se.se_part.Entity.Answers;
import com.se.se_part.Entity.Questionnaire;
import com.se.se_part.Entity.User;
import com.se.se_part.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.se.se_part.Service.UserService;

import java.util.List;

import static com.se.se_part.Main.userUid;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login") // @RequestParam("account")String account, @RequestParam("password")String password
    public Result login(@RequestBody User user)
    {
        User userForLogin = new User();
        userForLogin.setAccount(user.getAccount());
        userForLogin.setPassword(user.getPassword());
        Result result=userService.login(userForLogin);
        return result;
    }

    @PostMapping("/regist")
    public Result register(@RequestBody User user)
    {
        Result result = userService.register(user);
        return result;
    }

    @PostMapping("/createForm")
    public Result getgroupInfo(String token)
    {

        return null;
    }

    @PostMapping("/publish") //接口:publish 用于表示创建并发布表单
    public Result createNewForm(@RequestBody List<Questionnaire> questionnaires,@RequestParam String questionnaireTitle,@RequestHeader String token,@RequestParam List<Long> targetGroupIds)
    {

        Result result = userService.createNewForm(questionnaires, questionnaireTitle,token,targetGroupIds);
        return result;
    }

    @PostMapping("/createFormFindGroupBelongsTo")
    public Result getGroupBelongstoInfo(@RequestHeader String token)
    {
        Result result = userService.getGroupBelongstoInfo(token);
        return result;
    }

    @PostMapping("/createFormFindGroupAdministratedTo")
    public Result createFormFindGroupAdministratedTo(@RequestHeader String token)
    {
        Result result = userService.createFormFindGroupAdministratedTo(token);
        return result;
    }

    @PostMapping("/SubmitAnswer") //用户答完题后提交答案
    public Result submitAnswer(@RequestBody List<Answers> answers, @RequestParam Long questionnaireCoreId,@RequestHeader String token)
    {
        Result result = userService.createAnswerForm(answers,questionnaireCoreId,token);
        return result;
    }

    @PostMapping("/getAllFormIdAndTitle") //获取所有和用户相关的表单，包括其创建的和需要填写的
    public Result getAllFormsIdAndTitle(@RequestBody String token)
    {
        Result result = userService.getAllFormIdAndTitle(token);
        return result;
    }

    @PostMapping("/getWholeFormDetails") //获取某一个指定的问题卷的所有内容
    public Result getFormDetails(@RequestBody Long targetFormId)
    {
        Result result = userService.getWholeFormDetails(targetFormId);
        return result;
    }

    @PostMapping("/getAllAnswerFormIdAndFiller") //用户查询其所创建的某一个问卷的所有答案卷
    public Result getAllAnswerFormIdAndFiller(@RequestBody Long questionCoreId)
    {
        Result result = userService.getAllAnswerFormIdAndFiller(questionCoreId);
        return result;
    }

    @PostMapping("/getAnswerFormDetails")
    public Result getAnswerFormDetails(@RequestBody String token, @RequestBody Long questionCoreId)
    {
        Result result = userService.getAnswerFormDetails(token,questionCoreId);
        return result;
    }

    @PostMapping("/searchInfoByOneType") //用户查看某一张问卷中的某一道题(输入title)的作答情况
    public Result searchInfoByOneType(@RequestBody Long questionCoreId, @RequestBody String type)
    {
        Result result = userService.searchInfoByOneType(questionCoreId,type);
        return result;
    }

    @PostMapping("/searchFormCreated") //获取用户已经创建的表单
    public Result getCreatedFormTitleAndId(@RequestHeader String token)
    {
        Result result = userService.getCreatedFormTitleAndId(token);
        return result;
    }

    @PostMapping("/searchFormFinished") //获取用户已经填写的表单
    public Result getFinishedFormTitleAndId(@RequestHeader String token)
    {
        Result result = userService.getFinishedFormTitleAndId(token);
        return result;
    }

    @PostMapping("/searchFormNotFinished") //获取用户还没填写的表单
    public Result getNotFinishedFormTitleAndId(@RequestHeader String token)
    {
        Result result = userService.getNotFinishedFormTitleAndId(token);
        return result;
    }

    @GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestHeader String token)
    {
        Result result=userService.getUserInfo(token);
        return result;
    }

    @GetMapping("/test")
    public void test(@RequestBody List<Questionnaire> questionnaires,@RequestBody String questionnaireTitle,@RequestBody String token,@RequestBody List<Long> targetGroupIds)
    {
        for(int i=0;i<questionnaires.size();i++)
        {
            System.out.println(questionnaires.get(i));
        }
        System.out.println(questionnaireTitle);
        System.out.println(token);
        System.out.println(targetGroupIds);

    }

    @PostMapping("/testtest")
    public void test(@RequestParam("targetGroupIds[]")List<Questionnaire> targetGroupIds)
    {
        System.out.println(targetGroupIds);
    }


}
