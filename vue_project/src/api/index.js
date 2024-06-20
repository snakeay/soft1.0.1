import { da } from "element-plus/lib/locale/index.js";
import request from "../utils/request/"
import axios from 'axios';

// portal/findAllTypes
//获取分类列表
export const getfindAllTypes = () => {
  return request.get("portal/findAllTypes");
};

// 分页带条件查询所有头条
export const getfindNewsPageInfo = (info) => {
  return request.post("portal/findNewsPage",info);
};

// 查看头条详情
export const getshowHeadlineDetail = (id) => {
    return request({
        method: "post",
        url: "portal/showHeadlineDetail",
        headers: {
        "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
        },
         data:`hid=${id}`
  });
};

//删除的回调
// headline/removeByHid
export const removeByHid = (id) => {
    return request({
         method: "post",
            url: "headline/removeByHid",
            headers: {
            "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
            },
            data:`hid=${id}`
  })
};



//注册校验的接口  user/checkUserName
export const registerValidateApi = (username) => {
    return request({
         method: "post",
            url: "user/checkUserName",
            headers: {
            "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
            },
            data:`username=${username}`
  })
};


//判断用户登录过期的接口
export const isUserOverdue = () => {
  return request.get("user/checkLogin")
}

// 修改头条回显的接口
export const getFindHeadlineByHid = (id) => {
    return request({
        method: "post",
        url: "headline/findHeadlineByHid",
        headers: {
        "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
        },
         data:`hid=${id}`
  });
};

//点击保存修改的回调
// headline/update
export const saveOrAddNews = (news) => {
  return request.post("headline/update",news)
}

// 传递创建的表单
export const issueNews = (news) => {
  return request.post("headline/publish",news)
}










//登录的接口
export const getLogin = (info) => {
  return request.post("user/login",info);
};

// 注册的接口
export const registerApi = (userInfo) => {
  return request.post("user/regist",userInfo)
}

//获取用户信息的接口
export const getUserInfo = () => {
  const result= request.get("user/getUserInfo");
  console.log(result);
  return result;
};

//创建用户组的接口
export const createGroup = (groupname) => {
  console.log("groupname is " + groupname)
  return request.post("group/createGroup", null, {params: {
    groupName: groupname
  }});
}

//加入用户组的接口
export const joinGroup = (groupid) => {
  return request.post("group/joinGroup",null, {params: {
    groupId: groupid
  }});
}

//展示全部已创建的表
export const findAllCreateForm = () => {
  return request.post("user/searchFormCreated");
}

//展示全部已提交的表
export const findAllSubmitForm = () => {
  return request.post("user/searchFormFinished");
}

//展示全部未提交的表
export const findAllunSubmitForm = () => {
  return request.post("user/searchFormNotFinished");
}

//展示全部自己创建的用户组
export const findAllCreateGroup = () => {
  return request.post("group/findGroupCreated");
}

//展示全部自己加入的用户组
export const findAllJoinGroup = () => {
  return request.post("group/findGroupJoined");
}

//退出所选的用户组
export const exitJoinedGroup = (groupid) => {
  return request.post("group/exitGroup",null, {params: {
    groupId: groupid
  }});
}

//展示所选用户组所有用户
export const showGroupMember = (groupid) => {
  return request.post("group/displayMemberOfGroup",null, {params: {
    groupId: groupid
  }});
}

//删除所选用户
export const deleteMember = (groupid,memberid) => {
  return request.post("group/administrateGroup",null, {params: {
    groupId: groupid,
    userId:memberid
  }});
}

//解散所选用户组
export const deleteGroup = (groupid) => {
  return request.post("group/deleteGroup",null, {params: {
    groupId: groupid
  }});
}

//提交并发布表单
export const createNewForm = (questionList,formtitle,selectedGroups) => {
  return request.post("user/publish",questionList, {params: {
    questionnaireTitle: formtitle,
    targetGroupIds: selectedGroups
  }});
}

//获取问题卷
export const getForm = (formId) => {
  return request.post("user/getWholeFormDetails",null,{params: {
    targetFormId: formId,
  }});
}

//获取某一问卷某一题的答案
export const getQuestionDetail = (formId,questiontitle) => {
  return request.post("user/searchInfoByOneType",null,{params: {
    questionCoreId: formId,
    type: questiontitle
  }});
}

//提交表单
export const SubmitForm = (answer,formId) => {
  return request.post("user/SubmitAnswer",answer,{params: {
    questionnaireCoreId: formId,
  }});
}

//获取一个问题卷内的全部答案卷的答题者
export const getAllAnswerForms = (formId) => {
  return request.post("user/getAllAnswerFormIdAndFiller",null,{params: {
    questionCoreId: formId,
  }});
}

//获取某一个已填写的用户填写的答案卷
export const getUserAnswerForm = (answerformId) => {
  return request.post("admin/getAnswerFormDetails",null,{params: {
    answerCoreId: answerformId,
  }});
}


//测试函数
export const test = (data1) => {
  return request.post("user/test",null, {params: {
    targetGroupIds: data1
  }});
}
/*
export const test2 = (data1) => {
  return request.post("user/testtest",null, {params: {
    questionList: data1
  }});
}
*/
export const test2 = (questionList,data2,data3) => {

  return request.post("user/testtest",questionList, {params: {
    test: data2,
    arr: data3
  }});
}

/*---------------------------超级管理员部分---------------------------*/
//登录管理员
export const AdminLogin = (admindata) => {
  return request.post("/admin/adminLogin",admindata)
}


//查找数据库中的所有用户
export const getAllUsers = () => {
  return request.post("/admin/getAllUsers")
}

//通过用户id删除数据库中的用户
export const deleteUsers = (userId) =>{
  return request.post("/admin/deleteUsers",null,{params: {
    userId: userId
  }})
}

//查找数据库中的所有用户组
export const getAllGroups = () =>{
  return request.post("/admin/getAllGroups")
}

//查找特定用户组中的所有用户
export const checkGroupUsers = (groupId) =>{
  return request.post("/admin/checkGroupUsers",null,{params: {
    groupId: groupId
  }})
}

//删除特定用户组中的特定用户
export const deleteUserFormGroup = (groupId,userId) =>{
  return request.post("/admin/deleteUserFormGroup",null,{params: {
    groupId: groupId,
    userId: userId
  }})
}

//得到所有的问卷中心节点
export const getAllQuestionnaire = () =>{
  return request.post("/admin/getAllQuestionnaire")
}

//得到某一问卷的所有问题
export const adminGetQuestionnaireDetails = (questionnaireCoreId) => {
  return request.post("/admin/getQuestionnaireDetails",null,{params: {
    questionnaireCoreId: questionnaireCoreId
  }})
}

//得到特定问卷中心节点的所有答案中心节点
export const getAllAnswerForm = (questionnaireCoreId) =>{
  return request.post("/admin/getAllAnswerForm",null,{params: {
    questionnaireCoreId: questionnaireCoreId
  }})
}

//得到某一用户所填的答案
export const adminGetAnswerFormDetails = (answerCenterId) =>{
  return request.post("/admin/getAnswerFormDetails",null, {params:{
    answerCoreId : answerCenterId
  }})
}

export const adminDeleteForm = (questionnaireCoreId) =>{
  return request.post("/admin/adminDeleteForm",null,{params: {
    questionnaireCoreId: questionnaireCoreId
  }})
}