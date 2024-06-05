package com.se.se_part.Service;

import com.se.se_part.Entity.Group;
import com.se.se_part.Utils.Result;
import org.springframework.stereotype.Service;


public interface GroupService {
    Result joinGroup(Long userId, Long groupId);

    Result exitGroup(Long userId, Long groupId);

    Result createGroup(Long userId, String groupName);

    Result adminGroup(Long userId, Long groupId);

}
