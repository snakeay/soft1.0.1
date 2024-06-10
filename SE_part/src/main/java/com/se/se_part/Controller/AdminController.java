package com.se.se_part.Controller;

import com.se.se_part.Entity.Questionnaire;
import com.se.se_part.Entity.User;
import com.se.se_part.Service.AdminService;
import com.se.se_part.Service.UserService;
import com.se.se_part.Utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController
{
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    @PostMapping("/getAllUsers") //管理员获取所有的用户
    public Result getAllUsers()
    {
        Result result = adminService.getAllUsers();
        return result;
    }
    @PostMapping("/createUser")
    public Result createUser(@RequestBody User user) //管理员创建一个用户（选用）
    {
        Result result = userService.register(user);
        return result;
    }

    @PostMapping("/deleteUsers")
    public Result deleteUsers(@RequestBody List<Long> userIds)
    {
        Result result = adminService.deleteUsers(userIds);
        return  result;
    }

    @PostMapping("/createGroup")
    public Result createGroup(@RequestBody String groupName)
    {
        Result result = adminService.createGroup(groupName);
        return result;
    }

    @PostMapping("/getAllGroups")
    public Result getAllGroups()
    {
        Result result = adminService.getAllGroups();
        return result;
    }

    @PostMapping("/deleteGroup")
    public Result deleteGroup(@RequestBody Long groupId)
    {
        Result result = adminService.deleteGroup(groupId);
        return result;
    }

    @PostMapping("/checkGroupUsers")
    public Result checkGroup(@RequestBody Long groupId)
    {
        Result result = adminService.checkGroup(groupId);
        return result;
    }

    @PostMapping("/deleteUserFormGroup")
    public Result deleteUserFormGroup(@RequestBody Long userId, @RequestBody Long groupId)
    {
        Result result = adminService.deleteUserFormGroup(userId,groupId);
        return result;
    }

    @PostMapping("/joinUserIntoGroup")
    public Result joinUserIntoGroup(@RequestBody Long userId, @RequestBody Long groupId)
    {
        Result result = adminService.joinUserIntoGroup(userId,groupId);
        return result;
    }

    @PostMapping("/getAllQuestionnaire")
    public Result getAllQuestionnaires()
    {
        Result result = adminService.getAllquestionnaires();
        return result;
    }

    @PostMapping("/getQuestionnaireDetails")
    public Result getQuestionnaireDetails(@RequestBody Long questionnaireCoreId)
    {
        Result result = userService.getWholeFormDetails(questionnaireCoreId);
        return result;
    }

    @PostMapping("/getAllAnswerForm")
    public Result getAllAnswerForm(@RequestBody Long questionnaireCoreId)
    {
        Result result = userService.getAllAnswerFormIdAndFiller(questionnaireCoreId);
        return result;
    }

    @PostMapping("/getAnswerFormDetails")
    public Result getAnswerFormDetails(@RequestBody Long answerCoreId)
    {
        Result result = adminService.getAnswerFormDetails(answerCoreId);
        return result;
    }

}
