-- 系统配置表（可重复执行）
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

