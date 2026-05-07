-- 个人用户偏好（JSON）：默认提醒时间、提醒方式、重复提醒、默认视图、列表列等
-- 可重复执行：仅当列不存在时执行 ALTER

SET @db := DATABASE();
SET @sql := (
  SELECT IF(
    EXISTS(
      SELECT 1 FROM information_schema.COLUMNS
      WHERE TABLE_SCHEMA = @db AND TABLE_NAME = 'app_user' AND COLUMN_NAME = 'user_prefs'
    ),
    'SELECT 1 AS patch_app_user_user_prefs_ok',
    'ALTER TABLE app_user ADD COLUMN user_prefs text NULL COMMENT ''JSON: defaultReminderMinutes,reminderChannel,repeatReminder,defaultView,listColumns'' AFTER account_status'
  )
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
