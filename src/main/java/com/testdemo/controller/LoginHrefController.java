package com.testdemo.controller;
import com.testdemo.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Api(value = "login_href", description = "登录")
@Controller
@RequestMapping("/login_href")
public class LoginHrefController extends BaseController {
@ApiOperation(value = "跳转到系统公告管理页面")
@GetMapping("/idx_app_notice")
public String idxApp_notice() { 
return "idx_app_notice";
}
@ApiOperation(value = "跳转到团队日程留言交互管理页面")
@GetMapping("/idx_app_schedule_comment")
public String idxApp_schedule_comment() { 
return "idx_app_schedule_comment";
}
@ApiOperation(value = "跳转到日程任务分配管理页面")
@GetMapping("/idx_app_schedule_task")
public String idxApp_schedule_task() { 
return "idx_app_schedule_task";
}
@ApiOperation(value = "跳转到日程任务进度记录管理页面")
@GetMapping("/idx_app_task_progress")
public String idxApp_task_progress() { 
return "idx_app_task_progress";
}
@ApiOperation(value = "跳转到团队管理页面")
@GetMapping("/idx_app_team")
public String idxApp_team() { 
return "idx_app_team";
}
@ApiOperation(value = "跳转到团队协作日程管理页面")
@GetMapping("/idx_app_team_schedule")
public String idxApp_team_schedule() { 
return "idx_app_team_schedule";
}
@ApiOperation(value = "跳转到团队用户关联页面")
@GetMapping("/idx_app_team_user")
public String idxApp_team_user() { 
return "idx_app_team_user";
}
@ApiOperation(value = "跳转到用户管理页面")
@GetMapping("/idx_app_user")
public String idxApp_user() { 
return "idx_app_user";
}
@ApiOperation(value = "跳转到二级菜单页面")
@GetMapping("/idx_child_menu")
public String idxChild_menu() { 
return "idx_child_menu";
}
@ApiOperation(value = "跳转到系统菜单页面")
@GetMapping("/idx_menu")
public String idxMenu() { 
return "idx_menu";
}
@ApiOperation(value = "跳转到权限管理页面")
@GetMapping("/idx_permission_menu")
public String idxPermission_menu() { 
return "idx_permission_menu";
}
@ApiOperation(value = "跳转到角色管理页面")
@GetMapping("/idx_role_manage")
public String idxRole_manage() { 
return "idx_role_manage";
}
@ApiOperation(value = "跳转到管理员管理页面")
@GetMapping("/idx_sys_user")
public String idxSys_user() { 
return "idx_sys_user";
}
@ApiOperation(value = "跳转到个人中心页面")
@GetMapping("/idx_center")
public String idxCenter() {
return "idx_center";
}
@ApiOperation(value = "跳转到日程类型管理页面")
@GetMapping("/idx_schedule_type")
public String idxSchedule_type() {
return "idx_schedule_type";
}
@ApiOperation(value = "跳转到操作日志页面")
@GetMapping("/idx_operation_log")
public String idxOperation_log() {
return "idx_operation_log";
}
@ApiOperation(value = "跳转到系统配置页面")
@GetMapping("/idx_system_config")
public String idxSystem_config() {
return "idx_system_config";
}
@ApiOperation(value = "跳转到个人数据统计页面")
@GetMapping("/idx_personal_stats")
public String idxPersonal_stats() {
return "idx_personal_stats";
}
}
