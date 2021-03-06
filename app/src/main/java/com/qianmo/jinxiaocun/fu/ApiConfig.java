package com.qianmo.jinxiaocun.fu;

import com.qianmo.jinxiaocun.main.okhttp.base.OkhttpBase;

/**
 * anthor : wizardev
 * email : wizarddev@163.com
 * time : 17-12-7
 * desc :
 * version : 1.0
 */

public interface ApiConfig {
    //用户登录
    String LOGIN_STAFF = "/staff/logInStaff";
    //获取我要审批的列表
    String MY_APPLY_CLOCK_DETAILS_LIST = "/apply_fill_card/my_apply_clock_details_list";
    //获取审批详情
    String APPLY_CLOCK_DETAILS_ID = "/apply_fill_card/apply_clock_details_id";
    //获取审批人
    String TASK_STAFF = "/task/task_staff";
    //添加补卡审核
    String ADD_APPLY_FILL_CARD = "/apply_fill_card/add_applyfillcard";
    //修改审批内容
    String UPADTE_APPLYCLOCK_DETAILS = "/apply_fill_card/upadte_applyclock_details";
    //添加报销申请
    String ADD_APPLY_MATERIEL = "/apply_fill_card/add_apply_materiel";
//    String ADD_APPLY_MATERIEL = "/apply_fill_card/add_apply_materiels";
    //请假申请
    String ADD_APPLYLEAVE = "/apply_fill_card/add_applyleave";
    //获取我发布的审批
    String MY_RELEASE_CHECK = "/apply_fill_card/my_release_check";
    //上传图片
    String UPLOAD = "http://192.168.0.189:8080/file/upload";
    //获取待我执行的任务列表的数据
    String MY_WAIT_FOR_TASK = "/task/my_waitforTask";
    //我发布的
    String MY_TASK = "/task/my_task";
    //我执行的任务详情
    String TASK_DETAILS = OkhttpBase.BASE_URL+"/task/task_details";
    //获取发布的任务详情
    String MY_RELEASE_TASK_DETAILS = OkhttpBase.BASE_URL+"/task/my_release_task_details";
    //获取日报详情
    String GETDAILY_CARRYOUTID = OkhttpBase.BASE_URL+"/task/getdaily_carryOutid";
    //添加任务
    String INSERT_TASK = OkhttpBase.BASE_URL+"/task/insertTask";
    //添加日报
    String INSER_DAILY = OkhttpBase.BASE_URL+"/task/inser_daily";
    //添加会员
    String INCREASE_MEMBER = OkhttpBase.BASE_URL+"/member/increaseMember";
    //获取会员列表
    String STAFF_MEMBER = OkhttpBase.BASE_URL+"/member/staff_member";
    //获取项目类型
    String APPLY_REIMBURSE_TYPE = OkhttpBase.BASE_URL+"/apply_fill_card/apply_reimburse_type";
    //巡店查询
    String PATROL_STORE = OkhttpBase.BASE_URL+"/patrol_store";
    //获取门店列表
    String STORE = "http://192.168.0.124:8080/store";
    //添加巡店任务
    String ADD = "http://192.168.0.124:8080/app/patrol_store/add";

}
