/*
 Navicat Premium Data Transfer

 Source Server         : 密码1234561
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : js_20260205_3513

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 10/03/2026 10:50:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app_notice
-- ----------------------------
DROP TABLE IF EXISTS `app_notice`;
CREATE TABLE `app_notice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公告标题',
  `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公告内容',
  `publisher_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发布人ID',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '状态：1已发布 0未发布',
  `publish_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发布时间',
  `update_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统公告管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_notice
-- ----------------------------
INSERT INTO `app_notice` VALUES (1, '系统升级通知', '3月20日凌晨2点系统将进行升级，预计耗时2小时，期间系统暂停使用', '1', '1', '2026-03-10 09:00:00', '2026-03-10 09:00:00');
INSERT INTO `app_notice` VALUES (2, '新功能上线', '日程任务进度可视化功能已上线，可在个人中心查看', '1', '1', '2026-03-09 10:00:00', '2026-03-09 10:00:00');
INSERT INTO `app_notice` VALUES (3, '团队管理规则调整', '新增团队成员审核流程，需团队管理员手动审核加入申请', '1', '1', '2026-03-08 11:00:00', '2026-03-08 11:00:00');
INSERT INTO `app_notice` VALUES (4, '安全提醒', '请及时修改登录密码，建议每3个月更换一次', '1', '1', '2026-03-07 14:00:00', '2026-03-07 14:00:00');
INSERT INTO `app_notice` VALUES (5, '节假日安排', '3月15日放假1天，系统正常运行，紧急问题联系管理员', '1', '1', '2026-03-06 16:00:00', '2026-03-06 16:00:00');

-- ----------------------------
-- Table structure for app_schedule_comment
-- ----------------------------
DROP TABLE IF EXISTS `app_schedule_comment`;
CREATE TABLE `app_schedule_comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `schedule_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日程ID',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '留言人ID',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '留言内容',
  `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '父留言ID（回复留言时使用）',
  `create_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '留言时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '团队日程留言交互管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_schedule_comment
-- ----------------------------
INSERT INTO `app_schedule_comment` VALUES (1, '1', '3', '需求文档中有几处描述不清晰，需确认', '0', '2026-03-11 09:00:00');
INSERT INTO `app_schedule_comment` VALUES (2, '1', '2', '已收到，下午开会统一确认', '1', '2026-03-11 10:00:00');
INSERT INTO `app_schedule_comment` VALUES (3, '2', '4', '接口开发遇到问题，需延长1天工期', '0', '2026-03-18 14:00:00');
INSERT INTO `app_schedule_comment` VALUES (4, '3', '5', '测试用例已完成初稿，需审核', '0', '2026-03-22 11:00:00');
INSERT INTO `app_schedule_comment` VALUES (5, '4', '3', '海报已修改完成，请看最新版本', '0', '2026-03-11 15:00:00');
INSERT INTO `app_schedule_comment` VALUES (6, '1', '3', '需求文档中有几处描述不清晰，需确认', '0', '2026-03-11 09:00:00');
INSERT INTO `app_schedule_comment` VALUES (7, '1', '2', '已收到，下午开会统一确认', '1', '2026-03-11 10:00:00');
INSERT INTO `app_schedule_comment` VALUES (8, '2', '4', '接口开发遇到问题，需延长1天工期', '0', '2026-03-18 14:00:00');
INSERT INTO `app_schedule_comment` VALUES (9, '3', '5', '测试用例已完成初稿，需审核', '0', '2026-03-22 11:00:00');
INSERT INTO `app_schedule_comment` VALUES (10, '4', '3', '海报已修改完成，请看最新版本', '0', '2026-03-11 15:00:00');
INSERT INTO `app_schedule_comment` VALUES (11, '2', '1', '不错', NULL, '2026-03-10 10:41:06');
INSERT INTO `app_schedule_comment` VALUES (12, '2', '37', 'api对接中', NULL, '2026-03-10 10:49:12');

-- ----------------------------
-- Table structure for app_schedule_task
-- ----------------------------
DROP TABLE IF EXISTS `app_schedule_task`;
CREATE TABLE `app_schedule_task`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `schedule_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日程ID',
  `task_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务名称',
  `task_content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务内容',
  `assigner_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分配人ID',
  `assignee_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '被分配人ID',
  `deadline` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '截止时间',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '状态：0待确认 1已确认 2已完成 3已逾期',
  `create_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '日程任务分配管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_schedule_task
-- ----------------------------
INSERT INTO `app_schedule_task` VALUES (1, '1', '整理需求文档', '将产品需求文档整理为标准格式', '2', '3', '2026-03-14 18:00:00', '1', '2026-03-10 15:00:00', '2026-03-10 15:00:00');
INSERT INTO `app_schedule_task` VALUES (2, '2', '用户模块接口开发', '完成用户登录、注册接口开发', '1', '4', '2026-03-19 18:00:00', '2', '2026-03-10 15:05:00', '2026-03-10 15:05:00');
INSERT INTO `app_schedule_task` VALUES (3, '3', '编写测试用例', '编写日程任务模块测试用例', '1', '5', '2026-03-24 18:00:00', '1', '2026-03-10 15:10:00', '2026-03-10 15:10:00');
INSERT INTO `app_schedule_task` VALUES (4, '4', '制作推广海报', '制作系统推广宣传海报', '1', '3', '2026-03-11 18:00:00', '3', '2026-03-10 15:15:00', '2026-03-10 15:15:00');
INSERT INTO `app_schedule_task` VALUES (5, '5', '准备培训材料', '准备新员工系统操作培训材料', '1', '4', '2026-03-17 18:00:00', '0', '2026-03-10 15:20:00', '2026-03-10 15:20:00');
INSERT INTO `app_schedule_task` VALUES (6, '1', '整理需求文档', '将产品需求文档整理为标准格式', '2', '3', '2026-03-14 18:00:00', '1', '2026-03-10 15:00:00', '2026-03-10 15:00:00');
INSERT INTO `app_schedule_task` VALUES (7, '2', '用户模块接口开发', '完成用户登录、注册接口开发', '1', '4', '2026-03-19 18:00:00', '2', '2026-03-10 15:05:00', '2026-03-10 15:05:00');
INSERT INTO `app_schedule_task` VALUES (8, '3', '编写测试用例', '编写日程任务模块测试用例', '1', '5', '2026-03-24 18:00:00', '1', '2026-03-10 15:10:00', '2026-03-10 15:10:00');
INSERT INTO `app_schedule_task` VALUES (9, '4', '制作推广海报', '制作系统推广宣传海报', '1', '3', '2026-03-11 18:00:00', '3', '2026-03-10 15:15:00', '2026-03-10 15:15:00');
INSERT INTO `app_schedule_task` VALUES (10, '5', '准备培训材料', '准备新员工系统操作培训材料', '1', '4', '2026-03-17 18:00:00', '0', '2026-03-10 15:20:00', '2026-03-10 15:20:00');
INSERT INTO `app_schedule_task` VALUES (11, '2', 'api对接', 'api对接', '37', NULL, '2026-03-30', '0', '2026-03-10 10:48:32', '2026-03-10 10:48:32');
INSERT INTO `app_schedule_task` VALUES (12, '2', 'api对接', 'api对接', '37', NULL, '2026-03-31', '0', '2026-03-10 10:48:52', '2026-03-10 10:48:52');

-- ----------------------------
-- Table structure for app_task_progress
-- ----------------------------
DROP TABLE IF EXISTS `app_task_progress`;
CREATE TABLE `app_task_progress`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `task_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务ID',
  `user_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提交人ID',
  `progress` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '进度（百分比，如50）',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '进度描述',
  `submit_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提交时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '日程任务进度记录管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_task_progress
-- ----------------------------
INSERT INTO `app_task_progress` VALUES (1, '1', '3', '80', '需求文档已整理80%，剩余格式优化', '2026-03-14 10:00:00');
INSERT INTO `app_task_progress` VALUES (2, '2', '4', '100', '用户模块接口开发完成，已提交测试', '2026-03-19 16:00:00');
INSERT INTO `app_task_progress` VALUES (3, '3', '5', '50', '测试用例完成50%，剩余异常场景补充', '2026-03-23 14:00:00');
INSERT INTO `app_task_progress` VALUES (4, '4', '3', '100', '推广海报制作完成，已交付市场部', '2026-03-11 17:00:00');
INSERT INTO `app_task_progress` VALUES (5, '5', '4', '30', '培训材料初稿完成30%，待补充截图', '2026-03-16 09:00:00');
INSERT INTO `app_task_progress` VALUES (6, '1', '3', '80', '需求文档已整理80%，剩余格式优化', '2026-03-14 10:00:00');
INSERT INTO `app_task_progress` VALUES (7, '2', '4', '100', '用户模块接口开发完成，已提交测试', '2026-03-19 16:00:00');
INSERT INTO `app_task_progress` VALUES (8, '3', '5', '50', '测试用例完成50%，剩余异常场景补充', '2026-03-23 14:00:00');
INSERT INTO `app_task_progress` VALUES (9, '4', '3', '100', '推广海报制作完成，已交付市场部', '2026-03-11 17:00:00');
INSERT INTO `app_task_progress` VALUES (10, '5', '4', '30', '培训材料初稿完成30%，待补充截图', '2026-03-16 09:00:00');
INSERT INTO `app_task_progress` VALUES (12, 't1任务', '37', '50', '进度更新中', '2026-03-10');
INSERT INTO `app_task_progress` VALUES (13, '团本进度更新中', '36', '35', '团本进度更新中', '2026-03-10');

-- ----------------------------
-- Table structure for app_team
-- ----------------------------
DROP TABLE IF EXISTS `app_team`;
CREATE TABLE `app_team`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `team_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '团队名称',
  `creator_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `admin_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '团队管理员ID',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '团队描述',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '状态：1正常 0解散',
  `create_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '团队管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_team
-- ----------------------------
INSERT INTO `app_team` VALUES (1, '产品部', '1', '2', '负责产品设计与规划', '1', '2026-03-10 10:00:00', '2026-03-10 10:00:00');
INSERT INTO `app_team` VALUES (2, '研发部', '1', '1', '负责系统开发与维护', '1', '2026-03-10 10:05:00', '2026-03-10 10:05:00');
INSERT INTO `app_team` VALUES (3, '测试部', '1', '1', '负责系统测试与验收', '1', '2026-03-10 10:10:00', '2026-03-10 10:10:00');
INSERT INTO `app_team` VALUES (4, '市场部', '1', '1', '负责市场推广与运营', '1', '2026-03-10 10:15:00', '2026-03-10 10:15:00');
INSERT INTO `app_team` VALUES (5, '人事部', '1', '1', '负责人员招聘与管理', '1', '2026-03-10 10:20:00', '2026-03-10 10:20:00');
INSERT INTO `app_team` VALUES (6, '产品部', '1', '2', '负责产品设计与规划', '1', '2026-03-10 10:00:00', '2026-03-10 10:00:00');
INSERT INTO `app_team` VALUES (7, '研发部', '1', '1', '负责系统开发与维护', '1', '2026-03-10 10:05:00', '2026-03-10 10:05:00');
INSERT INTO `app_team` VALUES (8, '测试部', '1', '1', '负责系统测试与验收', '1', '2026-03-10 10:10:00', '2026-03-10 10:10:00');
INSERT INTO `app_team` VALUES (9, '市场部', '1', '1', '负责市场推广与运营', '1', '2026-03-10 10:15:00', '2026-03-10 10:15:00');
INSERT INTO `app_team` VALUES (10, '人事部', '1', '1', '负责人员招聘与管理', '1', '2026-03-10 10:20:00', '2026-03-10 10:20:00');
INSERT INTO `app_team` VALUES (11, 't1', '37', '36', '初始团队', '0', '2026-03-10 10:45:34', '2026-03-10 10:45:50');

-- ----------------------------
-- Table structure for app_team_schedule
-- ----------------------------
DROP TABLE IF EXISTS `app_team_schedule`;
CREATE TABLE `app_team_schedule`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `team_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '团队ID',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日程标题',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日程内容',
  `start_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结束时间',
  `creator_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '状态：1未开始 2进行中 3已完成 4已取消',
  `create_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '团队协作日程管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_team_schedule
-- ----------------------------
INSERT INTO `app_team_schedule` VALUES (1, '1', '产品需求评审', '评审V1.0版本产品需求文档', '2026-03-15 10:00:00', '2026-03-15 11:30:00', '2', '1', '2026-03-10 14:00:00', '2026-03-10 14:00:00');
INSERT INTO `app_team_schedule` VALUES (2, '2', '接口开发完成', '完成用户模块和团队模块接口开发', '2026-03-20 09:00:00', '2026-03-20 18:00:00', '1', '2', '2026-03-10 14:05:00', '2026-03-10 14:05:00');
INSERT INTO `app_team_schedule` VALUES (3, '3', '系统功能测试', '对日程管理系统进行全量功能测试', '2026-03-25 09:00:00', '2026-03-28 18:00:00', '1', '1', '2026-03-10 14:10:00', '2026-03-10 14:10:00');
INSERT INTO `app_team_schedule` VALUES (4, '4', '市场推广计划', '制定3月份系统推广计划', '2026-03-12 14:00:00', '2026-03-12 16:00:00', '1', '3', '2026-03-10 14:15:00', '2026-03-10 14:15:00');
INSERT INTO `app_team_schedule` VALUES (5, '5', '新员工入职培训', '对新入职员工进行系统操作培训', '2026-03-18 09:00:00', '2026-03-18 12:00:00', '1', '1', '2026-03-10 14:20:00', '2026-03-10 14:20:00');

-- ----------------------------
-- Table structure for app_team_user
-- ----------------------------
DROP TABLE IF EXISTS `app_team_user`;
CREATE TABLE `app_team_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `team_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '团队ID',
  `user_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户ID',
  `join_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加入类型：1申请加入 2邀请加入',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '状态：0待审核 1已加入 2已拒绝',
  `join_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加入时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '团队用户关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_team_user
-- ----------------------------
INSERT INTO `app_team_user` VALUES (1, '2', '1', '1', '1', '2026-03-30');
INSERT INTO `app_team_user` VALUES (2, '11', '37', '1', '1', '2026-03-10');

-- ----------------------------
-- Table structure for app_user
-- ----------------------------
DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `sex` int(2) NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `h_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `account_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '1正常0禁用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_user
-- ----------------------------
INSERT INTO `app_user` VALUES (1, 'cs1', '1', '张三', 1, '15802030203', '/app/20220624144105279_1656052865279.jpeg', '1');

-- ----------------------------
-- Table structure for child_menu
-- ----------------------------
DROP TABLE IF EXISTS `child_menu`;
CREATE TABLE `child_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `menu_icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `menu_href` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由地址',
  `index_page` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '序号',
  `menu_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主菜单',
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '二级菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of child_menu
-- ----------------------------
INSERT INTO `child_menu` VALUES (1, '系统公告管理', 'el-icon-setting', 'login_href/idx_app_notice', '1', '2', '2026-03-10 10:29:54');
INSERT INTO `child_menu` VALUES (2, '团队日程留言交互管理', 'el-icon-setting', 'login_href/idx_app_schedule_comment', '1', '2', '2026-03-10 10:29:54');
INSERT INTO `child_menu` VALUES (3, '日程任务分配管理', 'el-icon-setting', 'login_href/idx_app_schedule_task', '1', '2', '2026-03-10 10:29:54');
INSERT INTO `child_menu` VALUES (4, '日程任务进度记录管理', 'el-icon-setting', 'login_href/idx_app_task_progress', '1', '2', '2026-03-10 10:29:54');
INSERT INTO `child_menu` VALUES (5, '团队管理', 'el-icon-setting', 'login_href/idx_app_team', '1', '2', '2026-03-10 10:29:54');
INSERT INTO `child_menu` VALUES (6, '团队协作日程管理', 'el-icon-setting', 'login_href/idx_app_team_schedule', '1', '2', '2026-03-10 10:29:54');
INSERT INTO `child_menu` VALUES (7, '团队用户关联', 'el-icon-setting', 'login_href/idx_app_team_user', '1', '2', '2026-03-10 10:29:54');
INSERT INTO `child_menu` VALUES (8, '用户管理', 'el-icon-setting', 'login_href/idx_app_user', '1', '1', '2026-03-10 10:29:54');
INSERT INTO `child_menu` VALUES (9, '二级菜单', 'el-icon-setting', 'login_href/idx_child_menu', '1', '1', '2026-03-10 10:29:54');
INSERT INTO `child_menu` VALUES (10, '系统菜单', 'el-icon-setting', 'login_href/idx_menu', '1', '1', '2026-03-10 10:29:54');
INSERT INTO `child_menu` VALUES (11, '权限管理', 'el-icon-setting', 'login_href/idx_permission_menu', '1', '1', '2026-03-10 10:29:54');
INSERT INTO `child_menu` VALUES (12, '角色管理', 'el-icon-setting', 'login_href/idx_role_manage', '1', '1', '2026-03-10 10:29:54');
INSERT INTO `child_menu` VALUES (13, '管理员管理', 'el-icon-setting', 'login_href/idx_sys_user', '1', '1', '2026-03-10 10:29:54');
INSERT INTO `child_menu` VALUES (14, '个人中心', 'el-icon-setting', 'login_href/idx_center', '1', '1', '2026-03-10 10:29:54');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `menu_icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `index_page` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '序号',
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '系统管理', 'el-icon-setting', '1', '2026-03-10 10:29:54');
INSERT INTO `menu` VALUES (2, '功能管理', 'el-icon-setting', '1', '2026-03-10 10:29:54');

-- ----------------------------
-- Table structure for permission_menu
-- ----------------------------
DROP TABLE IF EXISTS `permission_menu`;
CREATE TABLE `permission_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `menu_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '一级菜单',
  `child_menu_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '二级菜单',
  `name_add` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '新增权限',
  `name_edit` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改权限',
  `name_delete` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除权限',
  `name_query` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '查询权限',
  `sys_user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属账号',
  `role_manage_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色',
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission_menu
-- ----------------------------
INSERT INTO `permission_menu` VALUES (1, '2', '1', '1', '1', '1', '1', '1', '1', '2026-03-10 10:29:54');
INSERT INTO `permission_menu` VALUES (2, '2', '2', '1', '1', '1', '1', '1', '1', '2026-03-10 10:29:54');
INSERT INTO `permission_menu` VALUES (3, '2', '3', '1', '1', '1', '1', '1', '1', '2026-03-10 10:29:54');
INSERT INTO `permission_menu` VALUES (4, '2', '4', '1', '1', '1', '1', '1', '1', '2026-03-10 10:29:55');
INSERT INTO `permission_menu` VALUES (5, '2', '5', '1', '1', '1', '1', '1', '1', '2026-03-10 10:29:55');
INSERT INTO `permission_menu` VALUES (6, '2', '6', '1', '1', '1', '1', '1', '1', '2026-03-10 10:29:55');
INSERT INTO `permission_menu` VALUES (7, '2', '7', '1', '1', '1', '1', '1', '1', '2026-03-10 10:29:55');
INSERT INTO `permission_menu` VALUES (8, '1', '8', '1', '1', '1', '1', '1', '1', '2026-03-10 10:29:55');
INSERT INTO `permission_menu` VALUES (9, '1', '9', '1', '1', '1', '1', '1', '1', '2026-03-10 10:29:55');
INSERT INTO `permission_menu` VALUES (10, '1', '10', '1', '1', '1', '1', '1', '1', '2026-03-10 10:29:55');
INSERT INTO `permission_menu` VALUES (11, '1', '11', '1', '1', '1', '1', '1', '1', '2026-03-10 10:29:55');
INSERT INTO `permission_menu` VALUES (12, '1', '12', '1', '1', '1', '1', '1', '1', '2026-03-10 10:29:55');
INSERT INTO `permission_menu` VALUES (13, '1', '13', '1', '1', '1', '1', '1', '1', '2026-03-10 10:29:55');
INSERT INTO `permission_menu` VALUES (14, '1', '14', '1', '1', '1', '1', '1', '1', '2026-03-10 10:29:55');
INSERT INTO `permission_menu` VALUES (15, '2', '5', '1', '1', '1', '1', '1', '2', '2026-03-10 10:35:32');
INSERT INTO `permission_menu` VALUES (16, '2', '6', '1', '1', '1', '1', '1', '2', '2026-03-10 10:35:44');
INSERT INTO `permission_menu` VALUES (17, '2', '7', '1', '1', '1', '1', '1', '2', '2026-03-10 10:35:48');
INSERT INTO `permission_menu` VALUES (18, '2', '4', '1', '1', '1', '1', '1', '2', '2026-03-10 10:35:54');
INSERT INTO `permission_menu` VALUES (19, '2', '3', '1', '1', '1', '1', '1', '2', '2026-03-10 10:35:57');
INSERT INTO `permission_menu` VALUES (20, '2', '2', '1', '1', '1', '1', '1', '2', '2026-03-10 10:36:01');
INSERT INTO `permission_menu` VALUES (21, '2', '2', '1', '1', '1', '1', '1', '3', '2026-03-10 10:36:12');
INSERT INTO `permission_menu` VALUES (22, '2', '3', '1', '1', '1', '1', '1', '3', '2026-03-10 10:36:21');
INSERT INTO `permission_menu` VALUES (23, '2', '4', '1', '1', '1', '1', '1', '3', '2026-03-10 10:36:25');
INSERT INTO `permission_menu` VALUES (24, '2', '1', '0', '0', '0', '1', '1', '3', '2026-03-10 10:36:36');
INSERT INTO `permission_menu` VALUES (25, '2', '1', '0', '0', '0', '1', '1', '2', '2026-03-10 10:36:42');
INSERT INTO `permission_menu` VALUES (26, '2', '4', '1', '1', '1', '1', '1', '4', '2026-03-10 10:43:41');

-- ----------------------------
-- Table structure for role_manage
-- ----------------------------
DROP TABLE IF EXISTS `role_manage`;
CREATE TABLE `role_manage`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_manage
-- ----------------------------
INSERT INTO `role_manage` VALUES (1, '系统管理员');
INSERT INTO `role_manage` VALUES (2, '团队');
INSERT INTO `role_manage` VALUES (3, '个人');
INSERT INTO `role_manage` VALUES (4, '其他');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USERNAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `role_manage_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色',
  `create_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '建立时间',
  `update_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  `signature` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注(可为空)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '管理员管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '1', '管理员', '1', '', '', '');
INSERT INTO `sys_user` VALUES (36, 'yg', '123456', '员工1', '3', '2026-03-10 10:44:11', '2026-03-10 10:44:11', '员工');
INSERT INTO `sys_user` VALUES (37, 't1', '123456', '团队1', '2', '2026-03-10 10:44:28', '2026-03-10 10:44:28', '团队');

SET FOREIGN_KEY_CHECKS = 1;
