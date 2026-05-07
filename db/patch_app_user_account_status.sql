-- 修复：Unknown column 'su.account_status' in 'field list'
-- 在「当前连接所选数据库」执行本脚本一次即可（Navicat / mysql 客户端连上业务库后执行）。
-- 若列已存在，下方动态 SQL 会变为 SELECT 1，不会重复 ALTER。
--
-- 执行成功后：当前代码中 App_userMapper 已依赖 account_status（禁用、个人用户登录判断）。
-- 若暂时不能加列，需从 App_userMapper.xml 的 Base_Column_List / getLoginValidation 中去掉 account_status 相关字段。

SET @sqlstmt := (
  SELECT CASE WHEN COUNT(*) > 0 THEN 'SELECT 1 AS patch_app_user_account_status_ok'
  ELSE 'ALTER TABLE app_user ADD COLUMN account_status varchar(10) DEFAULT ''1'' COMMENT ''1正常0禁用'' AFTER h_img'
  END
  FROM information_schema.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'app_user'
    AND COLUMN_NAME = 'account_status'
);
PREPARE stmt FROM @sqlstmt;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

UPDATE app_user SET account_status = '1' WHERE account_status IS NULL OR account_status = '';
