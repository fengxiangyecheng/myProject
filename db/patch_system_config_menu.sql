-- 系统配置菜单与权限（系统管理员 role_manage_id=1）

INSERT INTO child_menu (name, menu_icon, menu_href, index_page, menu_id, create_time)
SELECT '系统配置', 'el-icon-setting', 'login_href/idx_system_config', '1', '1', NOW()
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM child_menu WHERE menu_href = 'login_href/idx_system_config');

INSERT INTO permission_menu (menu_id, child_menu_id, name_add, name_edit, name_delete, name_query, sys_user_id, role_manage_id, create_time)
SELECT '1', CAST(cm.id AS CHAR), '1', '1', '1', '1', '1', '1', NOW()
FROM child_menu cm
WHERE cm.menu_href = 'login_href/idx_system_config'
  AND NOT EXISTS (
    SELECT 1 FROM permission_menu p
    WHERE p.role_manage_id = '1' AND p.child_menu_id = CAST(cm.id AS CHAR)
  );

