
export const staticRoutes = [
  {
    path: "/",
    redirect: "/Login",
  },
  {
    // 超级管理员登录界面
    path: "/adminlogin",
    component: () => import("../pages/AdminLogin/index.vue"),
    name: "AdminLogin",
  },
  {
    //填写表单界面
    path: "/fillform",
    component: () => import("../pages/FillForm/index.vue"),
    name: "FillForm",
  },
  {
    // 头条
    path: "/headlinenews",
    component: () => import("../pages/HeadlineNews/index.vue"),
    name: "HeadlineNews",
  },
  {
    // 登录
    path: "/login",
    component: () => import("../pages/Login/index.vue"),
    name: "Login",
  },
  {
    //注册
    path: "/register",
    component: () => import("../pages/Register/index.vue"),
    name: "Register",
  },
   {
    //发布新闻的页面
    path: "/addormodifynews",
    component: () => import("../pages/addOrModifyNews/index.vue"),
    name: "addOrModifyNews",
  },
  {
    //用户组
    path: "/group",
    component: () => import("../pages/Group/index.vue"),
    name: "Group",
  },
  {
    //用户组详细信息
    path: "/groupmember",
    component: () => import("../pages/GroupMember/index.vue"),
    name: "GroupMember",
  },
  {
    //选择需要发布表单给的用户组
    path: "/selectgroup",
    component: () => import("../pages/SelectGroup/index.vue"),
    name: "SelectGroup",
  },
  {
    //创建新表单
    path: "/createform",
    component: () => import("../pages/CreateForm/index.vue"),
    name: "CreateForm",
  },
  
  {
    //问题表单详情
    path: "/formdetail",
    component: () => import("../pages/FormDetail/index.vue"),
    name: "FormDetail",
  },
  {
    //答案表单详情
    path: "/answerformdetail",
    component: () => import("../pages/AnswerFormDetail/index.vue"),
    name: "AnswerFormDetail",
  },
  {
    //查询特定问题的作答答案详情
    path: "/questiondetail",
    component: () => import("../pages/QuestionDetail/index.vue"),
    name: "QuestionDetail",
  },
  {
    //查看填写了表单的所有用户
    path: "/selectuser",
    component: () => import("../pages/SelectUser/index.vue"),
    name: "SelectUser",
  },
  {
    //已创建的表单
    path: "/allformcreate",
    component: () => import("../pages/AllFormCreate/index.vue"),
    name: "AllFormCreate",
  },
  {
    //已提交的表单
    path: "/allformsubmit",
    component: () => import("../pages/AllFormSubmit/index.vue"),
    name: "AllFormSubmit",
  },
  {
    //未提交的表单
    path: "/allformunsubmit",
    component: () => import("../pages/AllFormunSubmit/index.vue"),
    name: "AllFormunSubmit",
  },
  
  /*---------------------------超级管理员部分---------------------------*/
  {
    //超级管理员引导界面
    path: '/administrator',
    name: 'Administrator',
    component: () => import('../pages/Administrator/index.vue'),
  },
  {
    //超级管理员用户管理界面
    path: '/usermanagement',
    name: 'UserManagement',
    component: () => import('../pages/Administrator/UserManagement.vue')
  },
  {
    //超级管理员用户组管理界面
    path: '/groupmanagement',
    name: 'GroupManagement',
    component: () => import('../pages/Administrator/GroupManagement.vue')
  },
  {
    //超级管理员用户组成员管理界面
    path: '/groupmembersmanagement',
    name: 'GroupMembersManagement',
    component: () => import('../pages/Administrator/GroupMembersManagement.vue')
  },
  {
    //超级管理员问卷数据查询界面
    path: '/surveymanagement',
    name: 'SurveyManagement',
    component: () => import('../pages/Administrator/SurveyManagement.vue')
  },
  {
    //超级管理员问卷数据查询时展示问卷题目界面
    path: '/admincheckquestions',
    name: 'AdminCheckQuestions',
    component: () => import('../pages/Administrator/AdminCheckQuestions.vue')
  },
  {
    //超级管理员问卷数据查询时展示问卷答案界面
    path: '/admincheckanswers',
    name: 'AdminCheckAnswers',
    component: () => import('../pages/Administrator/AdminCheckAnswers.vue')
  },
  {
    //超级管理员问卷数据查询时展示特定用户的问卷答案界面
    path: '/useranswers',
    name: 'UserAnswers',
    component: () => import('../pages/Administrator/UserAnswers.vue')
  },
  /*---------------------------超级管理员部分---------------------------*/
];
