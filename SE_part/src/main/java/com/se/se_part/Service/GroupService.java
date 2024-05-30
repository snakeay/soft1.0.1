package com.se.se_part.Service;

import com.se.se_part.Entity.Group;
import com.se.se_part.Utils.Result;
import org.springframework.stereotype.Service;


public interface GroupService {
    Result createGroup(Group group);
}
