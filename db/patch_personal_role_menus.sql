-- 个人用户 role_manage_id=3：个人数据统计菜单 + 个人中心权限（可重复执行）

INSERT INTO child_menu (name, menu_icon, menu_href, index_page, menu_id, create_time)
SELECT '个人数据统计', 'el-icon-data-analysis', 'login_href/idx_personal_stats', '1', '2', NOW()
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM child_menu WHERE menu_href = 'login_href/idx_personal_stats');

INSERT INTO permission_menu (menu_id, child_menu_id, name_add, name_edit, name_delete, name_query, sys_user_id, role_manage_id, create_time)
SELECT '2', CAST(cm.id AS CHAR), '0', '0', '0', '1', '1', '3', NOW()
FROM child_menu cm
WHERE cm.menu_href = 'login_href/idx_personal_stats'
  AND NOT EXISTS (
    SELECT 1 FROM permission_menu p
    WHERE p.role_manage_id = '3' AND p.child_menu_id = CAST(cm.id AS CHAR)
  );

INSERT INTO permission_menu (menu_id, child_menu_id, name_add, name_edit, name_delete, name_query, sys_user_id, role_manage_id, create_time)
SELECT '1', '14', '0', '1', '0', '1', '1', '3', NOW()
FROM DUAL
WHERE NOT EXISTS (
  SELECT 1 FROM permission_menu p WHERE p.role_manage_id = '3' AND p.child_menu_id = '14'
);
