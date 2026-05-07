-- 团队协作日程增强：负责人、可见范围、参与成员（可重复执行）

SET @db = DATABASE();

-- app_team_schedule: 负责人ID
SET @s1 = (
  SELECT CASE WHEN COUNT(*) > 0 THEN
    'SELECT 1 AS patch_team_schedule_leader_ok'
  ELSE
    'ALTER TABLE app_team_schedule ADD COLUMN leader_user_id varchar(10) NULL COMMENT ''负责人ID'' AFTER creator_id'
  END
  FROM information_schema.COLUMNS
  WHERE TABLE_SCHEMA = @db AND TABLE_NAME = 'app_team_schedule' AND COLUMN_NAME = 'leader_user_id'
);
PREPARE st1 FROM @s1; EXECUTE st1; DEALLOCATE PREPARE st1;

-- app_team_schedule: 可见范围（1全体成员 2指定成员）
SET @s2 = (
  SELECT CASE WHEN COUNT(*) > 0 THEN
    'SELECT 1 AS patch_team_schedule_visibility_ok'
  ELSE
    'ALTER TABLE app_team_schedule ADD COLUMN visibility_type varchar(10) NULL DEFAULT ''1'' COMMENT ''可见范围：1全体成员 2指定成员'' AFTER leader_user_id'
  END
  FROM information_schema.COLUMNS
  WHERE TABLE_SCHEMA = @db AND TABLE_NAME = 'app_team_schedule' AND COLUMN_NAME = 'visibility_type'
);
PREPARE st2 FROM @s2; EXECUTE st2; DEALLOCATE PREPARE st2;

-- 参与成员映射表
CREATE TABLE IF NOT EXISTS app_team_schedule_member (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  schedule_id varchar(10) NOT NULL COMMENT '团队日程ID',
  user_id varchar(10) NOT NULL COMMENT '成员用户ID(app_user.id)',
  create_time varchar(20) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_schedule_user (schedule_id, user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='团队日程参与成员';

