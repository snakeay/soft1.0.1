package com.se.se_part.Controller;

import com.se.se_part.Entity.Administrator;
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

    @PostMapping("/adminLogin") // @RequestParam("account")String account, @RequestParam("password")String password
    public Result login(@RequestBody Administrator admin)
    {
        Administrator adminForLogin = new Administrator();
        adminForLogin.setAccount(admin.getAccount());
        adminForLogin.setPassword(admin.getPassword());
        Result result=adminService.adminlogin(adminForLogin);
        return result;
    }
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
    public Result deleteUsers(@RequestParam Long userId)
    {
        Result result = adminService.deleteUsers(userId);
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
    public Result checkGroup(@RequestParam Long groupId)
    {
        Result result = adminService.checkGroup(groupId);
        return result;
    }

    @PostMapping("/deleteUserFormGroup")
    public Result deleteUserFormGroup(@RequestParam Long userId, @RequestParam Long groupId)
    {
        Result result = adminService.deleteUserFormGroup(userId,groupId);
        return result;
    }

    @PostMapping("/joinUserIntoGroup")
    public Result joinUserIntoGroup(@RequestParam Long userId, @RequestParam Long groupId)
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
    public Result getQuestionnaireDetails(@RequestParam Long questionnaireCoreId)
    {
        Result result = adminService.getWholeFormDetails(questionnaireCoreId);
        return result;
    }

    @PostMapping("/getAllAnswerForm")
    public Result getAllAnswerForm(@RequestParam Long questionnaireCoreId)
    {
        Result result = userService.getAllAnswerFormIdAndFiller(questionnaireCoreId);
        return result;
    }

    @PostMapping("/getAnswerFormDetails")
    public Result getAnswerFormDetails(@RequestParam Long answerCoreId)
    {
        Result result = adminService.getAnswerFormDetails(answerCoreId);
        return result;
    }

    @PostMapping("/adminDeleteForm")
    public Result adminDeleteForm(@RequestParam("questionnaireCoreId") Long questionnaireCoreId)
    {
        Result result = adminService.adminDeleteForm(questionnaireCoreId);
        return result;
    }

}
