package com.qianmo.jinxiaocun.fu;

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
}
