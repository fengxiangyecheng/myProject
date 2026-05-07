-- ============================================================
-- 需求书对齐：库表升级（请在业务库执行一次，库名与 application.yml 一致）
-- 执行前请备份。若列已存在会报错，可跳过对应 ALTER。
-- ============================================================

-- 1) 系统用户：账号状态、联系方式、个人设置 JSON
ALTER TABLE sys_user ADD COLUMN account_status varchar(10) DEFAULT '1' COMMENT '1正常0禁用' AFTER signature;
ALTER TABLE sys_user ADD COLUMN email varchar(100) NULL COMMENT '邮箱' AFTER account_status;
ALTER TABLE sys_user ADD COLUMN phone varchar(50) NULL COMMENT '电话' AFTER email;
ALTER TABLE sys_user ADD COLUMN sex varchar(10) NULL COMMENT '性别' AFTER phone;
ALTER TABLE sys_user ADD COLUMN user_prefs text NULL COMMENT 'JSON: defaultReminderMinutes,reminderChannel,defaultView,repeatReminder,listColumns' AFTER sex;

UPDATE sys_user SET account_status = '1' WHERE account_status IS NULL OR account_status = '';

-- 2) 团队：创建审核状态
ALTER TABLE app_team ADD COLUMN audit_status varchar(10) DEFAULT '1' COMMENT '0待审核1已通过2已拒绝' AFTER status;
UPDATE app_team SET audit_status = '1' WHERE audit_status IS NULL OR audit_status = '';

-- 3) 团队成员：子管理员角色
ALTER TABLE app_team_user ADD COLUMN team_role varchar(10) DEFAULT '1' COMMENT '1普通成员2子管理员' AFTER status;
UPDATE app_team_user SET team_role = '1' WHERE team_role IS NULL OR team_role = '';

-- 4) 任务：日程类型编码（关联 app_schedule_type.type_code）
ALTER TABLE app_schedule_task ADD COLUMN schedule_type_code varchar(50) NULL COMMENT '日程类型编码' AFTER status;

-- 5) 日程收藏
CREATE TABLE IF NOT EXISTS app_schedule_favorite (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id varchar(20) NOT NULL COMMENT '用户',
  task_id varchar(20) NOT NULL COMMENT 'app_schedule_task.id',
  create_time varchar(20) NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_user_task (user_id, task_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='个人日程收藏';

-- 6) 日程类型（系统管理员维护）
CREATE TABLE IF NOT EXISTS app_schedule_type (
  id int(11) NOT NULL AUTO_INCREMENT,
  type_code varchar(50) NOT NULL COMMENT '编码',
  type_name varchar(100) NOT NULL COMMENT '名称',
  sort_no int(11) DEFAULT 0,
  status varchar(10) DEFAULT '1' COMMENT '1启用0停用',
  remark varchar(255) NULL,
  create_time varchar(20) NULL,
  update_time varchar(20) NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_type_code (type_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日程类型';

INSERT IGNORE INTO app_schedule_type (type_code, type_name, sort_no, status, create_time, update_time) VALUES
('work', '工作', 1, '1', DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'), DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),
('life', '生活', 2, '1', DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'), DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),
('study', '学习', 3, '1', DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'), DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'));

-- 7) 操作日志
CREATE TABLE IF NOT EXISTS sys_operation_log (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  user_id varchar(20) NULL COMMENT '操作人',
  username varchar(100) NULL,
  action varchar(64) NOT NULL COMMENT '动作',
  detail varchar(500) NULL,
  ip varchar(64) NULL,
  create_time varchar(20) NULL,
  PRIMARY KEY (id),
  KEY idx_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志';

-- 8) 系统配置（键值）
CREATE TABLE IF NOT EXISTS sys_system_config (
  id int(11) NOT NULL AUTO_INCREMENT,
  config_key varchar(64) NOT NULL,
  config_value varchar(500) NULL,
  remark varchar(255) NULL,
  update_time varchar(20) NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_key (config_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置';

INSERT IGNORE INTO sys_system_config (config_key, config_value, remark, update_time) VALUES
('email_min_interval_sec', '300', '邮件提醒最小间隔秒', DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),
('default_reminder_options', '15,30,60,120', '默认提醒提前分钟选项', DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'));

-- 9) 个人用户（app_user）：与登录、系统管理员禁用/启用对齐
ALTER TABLE app_user ADD COLUMN account_status varchar(10) DEFAULT '1' COMMENT '1正常0禁用' AFTER h_img;
UPDATE app_user SET account_status = '1' WHERE account_status IS NULL OR account_status = '';

-- 可选：在 child_menu / permission_menu 中增加菜单指向：
-- login_href/idx_schedule_type  日程类型管理
-- login_href/idx_operation_log  操作日志
-- login_href/idx_personal_stats 个人数据统计（若使用独立页）
