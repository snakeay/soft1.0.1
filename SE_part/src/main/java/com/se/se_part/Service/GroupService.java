package com.se.se_part.Service;

import com.se.se_part.Utils.Result;


public interface GroupService {
    Result joinGroup(Long userId, Long groupId);

    Result exitGroup(Long userId, Long groupId);

    Result createGroup(Long userId, String groupName);

    Result adminGroup(Long userId, Long groupId);

    Result findGroupCreated(Long userId);

    Result findGroupJoined(Long userId);

    Result displayMemberOfGroup(Long groupId);

    Result deleteGroup(Long groupId);
}
