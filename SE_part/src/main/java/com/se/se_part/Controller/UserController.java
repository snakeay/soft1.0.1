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

    @PostMapping("/createFormFindGroupBelongsTo")
    public Result getGroupBelongstoInfo(String token)
    {
        Result result = userService.getGroupBelongstoInfo(token);
        return result;
    }

    @PostMapping("/createFormFindGroupAdministratedTo")
    public Result createFormFindGroupAdministratedTo(String token)
    {
        Result result = userService.createFormFindGroupAdministratedTo(token);
        return result;
    }


    @PostMapping("/publish") //接口:publish 用于表示发布表单
    public Result createNewForm(@RequestBody List<Questionnaire> questionnaires,@RequestBody String questionnaireTitle,@RequestBody String token,@RequestBody List<Long> targetGroupIds)
    {

        Result result = userService.createNewForm(questionnaires, questionnaireTitle,token,targetGroupIds);
        return result;
    }

    //@PostMapping("/getQuestionAnswers")
    //public Result getQuestionAnswers()

    @PostMapping("/SubmitAnswer") //用户答完题后提交答案
    public Result submitAnswer(@RequestBody List<Answers> answers,@RequestBody Long questionnaireCoreId)
    {
        Result result = userService.createAnswerForm(answers,questionnaireCoreId);
        return result;
    }


}
