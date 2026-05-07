-- 团队日程留言：放宽 ID 类字段，避免父留言/日程主键较长时更新失败（与 sql.sql 新建表保持一致）
ALTER TABLE `app_schedule_comment`
  MODIFY COLUMN `schedule_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日程ID',
  MODIFY COLUMN `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '留言人ID',
  MODIFY COLUMN `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '父留言ID（回复留言时使用）';
