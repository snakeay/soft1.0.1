package com.se.se_part.Service;

import com.se.se_part.Utils.Result;

import java.util.List;

public interface AdminService {
    Result getAllUsers();

    Result deleteUsers(Long userId);

    Result getAllGroups();

    Result checkGroup(Long groupId);

    Result deleteUserFormGroup(Long userId, Long groupId);

    Result joinUserIntoGroup(Long userId, Long groupId);

    Result deleteGroup(Long groupId);

    Result getAllquestionnaires();

    Result createGroup(String groupName);

    Result getAnswerFormDetails(Long answerCoreId);
}
